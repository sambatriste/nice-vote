<vote>
    <h3>お題: { opts.title }</h3>
    <p>現在{ items.length }つの候補があります。</p>
    <ul>
        <li each='{ items }'>
            { title }  <button onclick="{parent.vote}">{ num }</button>
        </li>
    </ul>
    <form onsubmit="{ add }">
        <input ref="newOpinionInput" onkeyup="{ editNewOpinion }" placeholder="意見" />
        <button disabled="{ !newOpinionText }">追加</button>
    </form>


    <p>現在の総投票数は{ total }票です。</p>
    <script>
   this.items = opts.items;
   this.total = sumVotes(this.items);

   this.on('update', function() {
       this.total = sumVotes(this.items);
   });

   vote(e) {
       e.item.num += 1;
       return true;
   }

   editNewOpinion(e) {
       this.newOpinionText = e.target.value;
   }

   add(e) {
       if (this.newOpinionText) {
           this.items.push({
              title: this.newOpinionText,
              num: 0
           });
           this.newOpinionText = '';
           this.refs.newOpinionInput.value = '';
       }
       e.preventDefault();
   }

   function sumVotes(itemsToCount) {
       return Object.keys(itemsToCount).reduce(function(previous, key) {
           return previous + itemsToCount[key].num;
       }, 0);
   }
  </script>
</vote>

