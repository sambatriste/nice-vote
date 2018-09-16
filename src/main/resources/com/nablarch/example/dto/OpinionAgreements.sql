FIND_OPINION=
select
    OP.OPINION_ID,
    OP.DESCRIPTION,
    count(AG.AGREEMENT_ID) AGREEMENT_COUNT
from
    OPINION OP
left join
    AGREEMENT AG
        on AG.OPINION_ID = OP.OPINION_ID
where OP.OPINION_ID = :opinionId
group by
    OP.OPINION_ID,
    OP.DESCRIPTION
order by
   OP.OPINION_ID