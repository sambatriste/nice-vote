FIND_OPINIONS=
select
    TH.THEME_ID,
    TH.TITLE,
    OP.OPINION_ID,
    OP.DESCRIPTION,
    count(AG.AGREEMENT_ID) AGREEMENT_COUNT
from
    THEME TH
inner join
    OPINION OP
        on OP.THEME_ID = TH.THEME_ID
left join
    AGREEMENT AG
        on AG.OPINION_ID = OP.OPINION_ID
where TH.THEME_ID = :themeId
group by
    OP.OPINION_ID,
    OP.DESCRIPTION
order by
   OP.OPINION_ID