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

    this.add = this.add.bind(this);
    this.editNewOpinion = this.editNewOpinion.bind(this);
  }

  componentDidMount() {
    const themeApi = `/api/theme/${this.state.themeId}`;
    fetch(themeApi)
      .then(data => data.json())
      .then(items => {
        this.setState(
          {
            title: items[0].title,
            items: items
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
    items.push(
      {
        themeId: this.state.themeId,
        title: this.state.title,
        description: this.state.newOpinion,
        agreementCount: 0
      }
    );
    this.setState(
      {
        items: items,
        newOpinion: ''
      }
    );

  }

  editNewOpinion(e) {
    this.setState({newOpinion: e.target.value});
  }

  render() {
    return (
      <div>
        <h3>お題: {this.state.title}</h3>
        <p>現在{this.state.items.length}つの候補があります。</p>
        <ul>{
          this.state.items.map((item, idx) => {
            return (
              <li key={idx}>
                {item.description}
                <button key={idx} onClick={event => this.vote(item)}>
                  {item.agreementCount}
                </button>
              </li>
            )
          }, this)
        }</ul>
        <form onSubmit={this.add}>
          <input type="text"
                 onChange={this.editNewOpinion}
                 value={this.state.newOpinion}
                 placeholder='意見'
          />
          <button disabled={!this.state.newOpinion}>追加</button>
        </form>
        <p>現在の総投票数は{this.sumAgreements(this.state.items)}票です。</p>
      </div>
    );
  }
}

export default Theme;