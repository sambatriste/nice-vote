import React from 'react';


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
  }

  componentDidMount() {
    const themeApi = `/api/theme/${this.state.themeId}`;
    fetch(themeApi)
      .then(data => data.json())
      .then(json => {
        this.setState({
          title: json.title,
          items: json.agreements
        });
      });
  }

  vote(item) {
    item.agreementCount += 1;
    this.setState(this.state);
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
    this.state = {
      idx: props.idx,
      item: props.item
    };
    this.onClick = this.onClick.bind(this);
  }

  onClick() {
    this.props.handleClick(this.state.item)
  }

  render() {
    const item = this.state.item;
    const idx = this.state.idx;
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