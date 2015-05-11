
* WaitingAnimation

- Ejemplo de tarea de larga duración que se ejecuta en un hilo independiente (thread):
  - Oculta un elemento de la interface principal (TextView).
  - Hace visible un Spinner animado mientras se realiza la tarea.
  - Oculta el Spinner, hace visible el elemento de la interface principal (TextView) y 
    muestra un mensaje (Toast) cuando la tarea ha finalizado.