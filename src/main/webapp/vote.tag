<vote>
    <h3>お題: { opts.title }</h3>
    <p>現在{ items.length }つの候補があります。</p>
    <ul>
        <li each='{ items }'>
            { title }  <button onclick="{parent.vote}">{ num }</button>
        </li>
    </ul>

    <p>現在の総投票数は{ total }票です。</p>
    <script>
   this.items = opts.items;

   this.total = sumVotes(this.items);

   vote(e) {
       var item = e.item;
       item.num += 1;
       this.total = sumVotes(this.items);
       return true;
   }

   function sumVotes(itemsToCount) {
       return Object.keys(itemsToCount).reduce(function(previous, key) {
           return previous + itemsToCount[key].num;
       }, 0);
   }
  </script>
</vote>

