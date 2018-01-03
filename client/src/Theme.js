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
    this.reloadThemes = this.reloadThemes.bind(this);
    this.aaa = this.aaa.bind(this);
  }

  componentDidMount() {
    const themeApi = `/api/theme/${this.state.themeId}`;
    axios.get(themeApi)
      .then(res => {
        this.setState({
          title: res.data.title,
          items: res.data.agreements
        });
      });
  }

  vote(item) {

    axios.post('/api/agreement', {
      opinionId: item.opinionId
    }).then(this.reloadThemes)

  }

  reloadThemes() {
    const themeApi = `/api/theme/${this.state.themeId}`;
    axios.get(themeApi)
      .then(this.aaa);
  }

  aaa(res) {
    this.setState({
      title: res.data.title,
      items: res.data.agreements
    });
  }


  sumAgreements(itemsToCount) {
    return Object.keys(itemsToCount).reduce((previous, key) => {
      return previous + itemsToCount[key].agreementCount;
    }, 0);
  }

  add(e) {

    e.preventDefault();

    if (!this.state.newOpinion) {
      return;
    }
    const items = this.state.items;
    items.push({
      themeId: this.state.themeId,
      title: this.state.title,
      description: this.state.newOpinion,
      agreementCount: 0
    });
    this.setState({
      items: items,
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
    const item = this.props.item;
    const idx = this.props.idx;
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