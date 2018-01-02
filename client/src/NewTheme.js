import React from 'react';

class NewTheme extends React.Component {
  render() {
    return (
      <div>
        <h2>新規テーマ登録</h2>
        <input type="text"
               placeholder='新しいテーマ'/>
        <button>追加</button>
      </div>
    );
  }
}

export default NewTheme