import React from 'react';
import {BrowserRouter, Route, Link} from 'react-router-dom';
import Themes from './Themes';
import Theme from './Theme';
import NewTheme from './NewTheme';

class App extends React.Component {

  render() {
    return (
      <BrowserRouter>
        <div>
          <ul>
            <li><Link to='/themes'>投票一覧</Link></li>
            <li><Link to='/new'>新規テーマ登録</Link></li>
          </ul>

          <hr/>

          <Route exact path='/themes' component={Themes}/>
          <Route path='/themes/:id' component={Theme}/>
          <Route path='/new' component={NewTheme}/>
        </div>
      </BrowserRouter>
    )
  }
}

export default App;
