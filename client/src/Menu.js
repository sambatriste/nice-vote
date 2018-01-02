import React from 'react';
import { Link } from 'react-router-dom'

class Menu extends React.Component {

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <ul>
        <li><Link to='/themes'>投票一覧</Link></li>
        <li><Link to='/new'>新しい投票</Link></li>
      </ul>
    )
  }
}

export default Menu