# BusTicketBookingSystem
A simple android mobile application developed using Java in Android Studio

The project introduces you to the front-end development of an android bus ticket booking mobile application. 
The app features an initial Login/SignUp page where the credentials used includes a user mobile number and a customized password. For a new user, a SignUp button 
redirects to a SignUp activity, where the user is asked to fill up his/her details like first and last name, username, email id, phone number and 
to create a password for the account.
In the SignUp Activity, the details includes validations for email address format, valid phone number (10 characters and all digits), and required field checks.
The password must contain atleast one special character, and uppercase character and length of 8 for better security.
Once the details are fed properly, the user signups and is redirected to the login activity.
The code implementation for authentication incorporates the use of two arrays, one for phone numbers and the other for passwords. The login phone number and password
is checked for compatability with respect to the array entries and positions.

On successful login, the user enters the main Booking Activity. Here the user can select from various starting points and destinations. As an inter-city bus system,
the rate between bus stops is standardized to a constant amount. According to number of passengers, the fare for a bus journey is calculated. 

To facilitate the payment, Google pay integration has been implemented. On click of a booking button, the code checks whether the user 
has an active google pay application installed, and redirects to the application. The user will be directed to the final-step of entering the pin, for the calculated amount automatically.

On successful payment, returning back to the application, the user sees a Qr generation Activity, where his/her ticket for the journey will be generated with all 
the details regarding date, start, destination, a unique transaction id and QR code to be scanned in real-time.

The code further implements automatic saving of the generated ticket into the device's gallery as an image, enhancing user experience and friendliness.
The aesthetic user interface is achieved with the help of FIGMA tool for design.



![TicketSignUp](https://github.com/NavneethMNambiar/BusTicketBookingSystem/assets/121511892/bddac3fe-0899-4421-8adf-8871d9bf9bbe)
![TicketLogin](https://github.com/NavneethMNambiar/BusTicketBookingSystem/assets/121511892/d0468344-ea86-4a39-b66f-d616d6674b5d)
![TicketGeneration](https://github.com/NavneethMNambiar/BusTicketBookingSystem/assets/121511892/3643c246-6fd6-46f9-880b-7f428e16fab4)
![TicketBooking](https://github.com/NavneethMNambiar/BusTicketBookingSystem/assets/121511892/18536bc4-bc6a-4964-978b-5961fdf5f469)
