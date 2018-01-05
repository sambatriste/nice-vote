import React from 'react';
import axios from 'axios';

class Theme extends React.Component {

  constructor(props) {
    super(props);
    const themeId = props.match.params.id;
    this.state = {
      themeId: themeId,
      items: [],
      title: '',
      newOpinion: '',
    };
    this.vote = this.vote.bind(this);
    this.add = this.add.bind(this);
    this.editNewOpinion = this.editNewOpinion.bind(this);
    this.fetchThemes = this.fetchThemes.bind(this);
  }

  componentDidMount() {
    this.fetchThemes();
  }

  vote(item) {
    axios.post('/api/agreement', {
      opinionId: item.opinionId
    }).then(this.fetchThemes);
  }

  fetchThemes() {
    const updateState = res => {
      this.setState({
        title: res.data.title,
        items: res.data.agreements
      });
    };
    axios.get(`/api/theme/${this.state.themeId}`)
         .then(updateState);
  }


  sumAgreements(items) {
    return Object.keys(items).reduce((previous, key) => {
      const item = items[key];
      return previous + item.agreementCount;
    }, 0);
  }

  add(e) {

    e.preventDefault();

    if (!this.state.newOpinion) {
      return;
    }

    const newItem = {
      themeId: this.state.themeId,
      title: this.state.title,
      description: this.state.newOpinion,
      agreementCount: 0
    };
    const newItems = this.state.items.concat(newItem);
    this.setState({
      items: newItems,
      newOpinion: ''
    });
  }

  editNewOpinion(event) {
    this.setState({
      newOpinion: event.target.value
    });
  }

  render() {
    const sum = this.sumAgreements(this.state.items);
    return (
      <div>
        {this.renderItems(this.state.title, this.state.items)}
        {this.renderForm(this.state.newOpinion)}
        <p>現在の総投票数は{sum}票です。</p>
      </div>
    );
  }

  renderItems(title, items) {
    return (
      <div>
        <h3>お題: {title}</h3>
        <p>現在{items.length}つの候補があります。</p>
        <ul>{
          items.map((item, idx) => {
            return (<Opinion idx={idx} item={item} handleClick={this.vote}/>)
          }, this)
        }</ul>
      </div>
    );
  }

  renderForm(newOpinion) {
    return (
      <div>
        <form onSubmit={this.add}>
          <input type="text"
                 onChange={this.editNewOpinion}
                 value={newOpinion}
                 placeholder='意見'
          />
          <button disabled={!newOpinion}>追加</button>
        </form>
      </div>
    );
  }
}

class Opinion extends React.Component {
  constructor(props) {
    super(props);
    this.onClick = this.onClick.bind(this);
  }

  onClick() {
    this.props.handleClick(this.props.item)
  }

  render() {
    const { item, idx } = this.props;
    return (
      <li key={idx}>
        {item.description}
        <button key={idx} onClick={this.onClick}>
          {item.agreementCount}
        </button>
      </li>
    );
  }
}

export default Theme;