import React from 'react';
import {Link} from 'react-router-dom'

class Themes extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      themes: []
    };
  }

  componentDidMount() {
    fetch('/api/theme')
      .then(data => data.json())
      .then(json => {
        this.setState({
          themes: json
        });
      });
  }

  render() {
    return (
      <div>
        <h2>テーマ一覧</h2>
        <ul>{
          this.state.themes.map((theme, idx) => {
            return (
              <li key={idx}>
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