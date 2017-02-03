<theme>
  <ul>
    <li each='{themes}'><a href='/api/theme/{themeId}'>{ title }</a></li>
  </ul>
  var self = this;
  fetch('/api/theme')
  .then(function(data) {
    return data.json();
  })
  .then(function(json) {
    self.themes = json;
    self.update()
  });

</theme>