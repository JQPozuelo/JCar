# JCar 
JCar es una aplicación enfocada a la gestion de los mantenimientos de los vehículos esta aplicacion te permitira registrar tu vehículo en la base de datos del taller 
que sueles acudir y el taller sera el encargado de registrar los mantenimientos a realizar y cuando el vehículo se encuentre en el taller realizando la revisión 
tu podras observar que se le esta realizando al vehículo mediante una actualización de la ficha del mismo que actualizara el mecánico, asi tu podras llevar un control 
de los mantenimientos, además el mecánico te indicará cuales pueden ser los siguientes mantenimientos o reparaciones a realizar.
## Como se usa
1. **Login**
El usuario dispondrá de una pantalla de logueo en la cual podrá observar 2 campos de texto para introducir sus datos.
2. **Registro** 
En caso de no tener cuenta en la aplicación dispondrá de una pantalla de logueo la cual recogera en 3 campos de texto, en el primero debe introducir su correo el usuario,
en el segundo debe introducir una contraseña de minímo 6 caracteres y que contenga 1 número y por ultimo este campo debe contener la misma contraseña que el campo donde
se introdujo la primera.
El usuario administrador vendrá con una contraseña y un correo dado por defecto a la aplicación, estos datos se le proporcionarán al usuario que vaya a realizar
estas funciones.
3. **Reseteo de contraseña**
Para poder acceder a la pantalla que contiene la manera de poder resetearla, en la pantalla de login habrá una frase que será Restablecer contraseña, pinche en ella
y le llevara en la que podra introducir el correo para recuperar su contraseña. Ojo si el correo no existe no llegara nada nunca. Bueno despues de darle al boton de 
restablecer contraseña este mandara un mensaje con un link a una página para restablecer, a veces el mensaje llega a la mensajeria de spam.
4. **Menu**
Aqui en el menu los usuarios dispondran de botones que les indicaran a donde pueden moverse para realizar las acciones que contiene la aplicación.
5. **Crear Vehículo**
Aquí podrás crear la ficha de tu vehículo introduciendo los datos del mismo la marca y el modelo las elegiras entre las opciones marcas y modelos que te dispondrá la 
aplicación  y en función de la marca que escojas te saldran los modelos a seleccionar.
6. **Mostrar Vehículos**
Aquí segun el usuario que este logueado si es el administrador este verá una pantalla en la cual él sabiendo que usuario tiene que buscar, lo introducirá en el campo
de texto indicado para ello. Si entra un usuario normal esta pantalla reconocera el usuario que esta logueado y cargara automaticamente los vehículos que este usuario
tiene guardados. Y este vera en Recycler View el emblema del vehículo que cambiara en función de la marca del mismo se verá la disponibilidad del vehículo, si esta disponible
el boton estará en verde, si esta recepcionado cambiara a naranja y si esta en reparación cambiará a rojo, el resto de datos que se verán son la matrícula y el modelo.
Cuando el usuario que sea administrador una vez tenga cargados los vehículos del usuario que haya elegido este al seleccionar el vehículo a modificar, se cambiara a otra
pantalla que mostrara todos los datos y este podrá modificar los estados solo se puede dar de nombre de estado Disponible, Recepcionado y En reparacion, para que el boton
anteriormente seleccionado cambie de color correctamente, a su vez dispondrá de un campo de texto que estará vacio la primera vez que se vean los datos del vehículo, donde
el mecánico escribira lo realizado durante el mantenimiento y cualquier posible problema encontrado durante la reparación o matenimiento, para que el cliente tenga constancia 
de ello, es decir el administrador dispondrá de la funcion de actualizar.
Un usuario normal podrá ver los mismos datos que el administrador, si pincha al objeto cargado en el Recycler View le llevara a la misma pantalla que al administrador
pero este no podra modificar ningún dato solo podra eliminar el vehículo por si en algún momento ya no lo posee.
7. **Mantenimientos**
En esta pantalla el usuario administrador tendrá la capacidad de poder crear entradas tomando como referencia la matricula del vehículo para poder identificarlo y dentro
de este documento este usuario escribirá lo que se le puede realizar al vehículo proximamente por ejemplo a los 200.000 km cambio de liquido de la caja de cambios o 
a los 3 años, este documento podrá ser actualizado y borrado por el administrador.
El cliente en esta pantalla solo podrá buscar su matrícula y ver lo anotado por el mecánico de estas recomendaciones.
