**
Har skapat en StudentService interface för att förstaraka säkerhet 
i method ändringar. Sedan har implementerat 
CDI(Contexts and Dependency Injection) i StudentRest klassen genom att 
använda @Inject annotation. 

Just nu StudentDaoJpa gör ingenting. Tanken med det att utveckla vidare med säkerhet function. 
**

POST: CREATE A NEW STUDENT
http://localhost:8080/student-management-system/api/v1/student

{
"firstName":"Arif",               
"lastName":"Hossain",             
"email":"arif@gmail.com",         
"phoneNumber":"0763209865"
}

//firstName måste vara minst två bokstav och kan inte vara null. 
  Skickar man en request utan firstName, får man 404 not found med ett meddelande "FIRSTNAME_CONNOT_BE_NULL"

//lastName måste vara minst två bokstav OCH kan inte vara null
  Skickar man en request utan lastName, får man 404 not found med ett meddelande "LASTNAME_CONNOT_BE_NULL"

//email måste vara unique och inte få vara null annas få man 
  404 Not Found Exception med meddelande EMAIL_CANNOT_BE_NULL 
  409 Conflict Exception med meddelande EMAIL_ALREADY_EXIST

GET: GET ALL STUDENT
http://localhost:8080/student-management-system/api/v1/student

GET: GET STUDENT BY ID
http://localhost:8080/student-management-system/api/v1/student/{id}

*** Hämta en student med id. Om id existerar inte då får man 404 Not Found Exception och ett medelande
"ID_'2'_IS_NOT_VALID_STUDENT_ID!PLEASE_TRY_WITH_VALID_ID!"


GET: GET STUDENT BY LASTNAME
http://localhost:8080/student-management-system/api/v1/student/lastName/{lastName}

*** Hämta en student med efternamn. Om efternamn inte fins då får man 404 Not Found Exception och ett medelande
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

Eller ALLA

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

DELETE: DELETE A STUDENT
http://localhost:8080/student-management-system/api/v1/student/{ID}

*** Tar bort student med id. Om id existerar inte då får man 404 Not Found Exception och ett medelande
"ID_'2'_IS_NOT_VALID_STUDENT_ID!PLEASE_TRY_WITH_VALID_ID!"
