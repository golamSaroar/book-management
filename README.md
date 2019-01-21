I have always wanted to have my own library. So I am frequently purchasing books and lending
them to various people more often than I can keep track of. Hence I thought of creating a 
simple application for managing my books with ease.

*Spring Data JPA has been used with HATEOAS.*

User can add Books with Authors and Categories. User can also add borrowers when they want 
to lend books.

Once a book is borrowed, it's status changes from _Available_ to _On Loan_. When the book is returned, status is again changed to _Available_. And if the book is lost, status is set to 
_LOST_, also recording the borrower who lost the book. Each Loan is recorded in the database. 

At any time, User can see lists of Books, Authors, Categories, Borrowers and Loan History.
In Book view, User can see book detail along with the book's Loan History.
In Author view, User can see all the books by this author.
Similarly, in Category view, User can see list of books of that category.
In Borrower view, User can see all the books they have borrowed and if they lost some books, 
the name of those books.
Finally in Loan view, User can see the detail of the loan including loan date, loan status (active, returned or lost) and if returned, the return date.

However, I haven't written any test code or authentication code. 


## Instruction to build
Project is based on Maven and can be built using the following command

### Clean Before Build:

`mvn clean`

### Package creation:

`mvn package`

It will validate the build. The build package will be available to target folder.


## Instruction to run
### IDE
Run the program with main method from `com.mybooks.api.ApiApplication` class

### Command-line

`mvn spring-boot:run`

** You have to have 8080 port free to run this application.

After running the application you can access it with Swagger UI using the URL below -

[http://localhost:8080/swagger-ui.html#/](http://localhost:8080/swagger-ui.html#/)
