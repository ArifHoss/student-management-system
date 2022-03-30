POST: CREATE A NEW STUDENT
http://localhost:8080/student-management-system/api/v1/student

{
"firstName":"Arif",               //måste vara minst två bokstav
"lastName":"Hossain",             //måste vara minst två bokstav
"email":"arif@gmail.com",         // måste vara unique annas få du EMAIL_ALREADY_EXIST medelande
"phoneNumber":"0763209865"
}

GET: GET ALLL STUDENT
http://localhost:8080/student-management-system/api/v1/student

GET: GET STUDETN BY ID
http://localhost:8080/student-management-system/api/v1/student/{id}

*** Hämta en student med id. Om id existerar inte då får man 404 Not Found Exception och ett medelande
"ID_'2'_IS_NOT_VALID_STUDENT_ID!PLEASE_TRY_WITH_VALID_ID!"


GET: GET STUDENT BY LASTNAME
http://localhost:8080/student-management-system/api/v1/student/lastName/{lastName}

*** Hämta en student med efternam. Om efternam inte fins då får man 404 Not Found Exception och ett medelande
"LASTNAME_Hossai_NOT_EXIST"

PATCH: UPDATE ALL VALUE OR SPECIFIC VALUE
http://localhost:8080/student-management-system/api/v1/student/value/{id}

*** Update en value eller flera samtidigt
{
"email":"arif@gmail.se" 
}
{
"firstName":"Arif",
"lastName":"Hossain",
"email":"arif@gmail.com"
}

eller ALLA

// Om email existerar då får man en 409-Conflict Exception med en String message
// EMAIL_arif@gmail.se_ALREADY_EXIST!TRY_WITH_ANOTHER_EMAIL


PUT: UPDATE ALL STUDENT
 http://localhost:8080/student-management-system/api/v1/student

{
"id":1,
"firstName":"Sumon",
"lastName":"Rahman",
"email":"arif@gmail.com",
"phoneNumber":"0763209865"
}
