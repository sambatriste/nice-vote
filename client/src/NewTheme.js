import React from 'react';
import axios from 'axios';

class NewTheme extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      title: ''
    };
    this.editTheme = this.editTheme.bind(this);
    this.postNewTheme = this.postNewTheme.bind(this);
  }


  postNewTheme() {
    axios.post('/api/theme', {
      title: this.state.title
    }).then(res => {
        this.props.history.push('/themes');
      }
    ).catch(res => console.log(res));
  }

  editTheme(event) {
    this.setState({
      title: event.target.value
    });
  }


  render() {
    const title = this.state.title;
    return (
      <div>
        <form onSubmit={this.postNewTheme}>
          <h2>新規テーマ登録</h2>
          <input type="text"
                 onChange={this.editTheme}
                 value={title}
                 placeholder='新しいテーマ'
          />
          <button disabled={!title}>追加</button>
        </form>
      </div>
    );
  }
}

export default NewTheme