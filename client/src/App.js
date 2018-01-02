import React from 'react';
import {BrowserRouter, Route } from 'react-router-dom';
import Menu from './Menu';
import Themes from './Themes';
import Theme from './Theme';
import NewTheme from './NewTheme';

class App extends React.Component {

  render() {
    return (
      <BrowserRouter>
        <div>
          <Route exact path='/' component={Menu}/>
          <Route exact path='/themes' component={Themes}/>
          <Route path='/themes/:id' component={Theme} />
          <Route path='/new' component={NewTheme}/>
        </div>
      </BrowserRouter>
    )
  }
}

export default App;
