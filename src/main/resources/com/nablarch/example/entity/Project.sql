FIND_PROJECT =
SELECT
    *
FROM
    PROJECT
WHERE
    $if(clientId)     {CLIENT_ID = :clientId}
    AND $if(projectName) {PROJECT_NAME LIKE  :%projectName%}
