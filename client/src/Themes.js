import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

class Themes extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      themes: []
    };
  }

  componentDidMount() {
    axios.get('/api/theme').then(res => {
      this.setState({
        themes: res.data
      });
    });
  }

  render() {
    return (
      <div>
        <h2>テーマ一覧</h2>
        <ul>{
          this.state.themes.map(theme => {
            return (
              <li key={theme.themeId}>
                <Link to={`/themes/${theme.themeId}`}>{theme.title}</Link>
              </li>
            );
          })
        }</ul>
      </div>
    );
  }
}

export default Themes