---
name: issues micro-optimizaciones
about: Create a report to help us improve
title: ''
labels: ''
assignees: ''

---

## Descripción del problema

Describe de manera concreta dónde se va a aplicar la micro-optimización y el motivo por el cual beneficiará el rendimiento de la aplicación.

## Afectación

- [ ] Ralentización de la interfaz
- [ ] Falta de responsividad de eventos
- [ ] ANR
- [ ] Leak o fuga de memoria
- [ ] Uso excesivo o crecimiento incontrolado de la memoria
- [ ] Recolección de memoria excesiva
- [ ] Uso inadecuado de una API
- [ ] Uso inadecuado de componentes

## Dispositivo en el que se verifica el problema y posterior validación de la mejora

- **Dispositivo en el que se realiza la prueba**: [Modelo del teléfono]
- **Sistema operativo**: [Versión del sistema operativo]

## Comportamiento actual

Describe cómo está funcionando actualmente. ¿Se está produciendo un retraso o algún problema relacionado con el rendimiento (como animaciones lentas, uso elevado de CPU, etc.)?

## Sugerencia de solución

Describe qué micro-optimización podría realizarse para mitigar o resolver el problema descrito, mejorando el rendimiento de la aplicación.

## Revisión del rendimiento actual

- **Perfilamiento**: [Herramienta usada para perfilar la app]
- **Consumo de CPU**: ¿Existen anormalidades en el uso del procesador?
- **Memoria**: ¿Existen fugas de memoria (memory leaks) o un uso excesivo de la memoria (memory bloats)?
- **Tiempo de carga de la app**: ¿La app se demora en cargar en alguna parte de la app?

## ¿Qué micro-optimización podría aplicarse?

- [ ] Reducción del uso de memoria
- [ ] Mejora en la carga de la aplicación
- [ ] Optimización de procesos en segundo plano
- [ ] Mejoras en la eficiencia de red (por ejemplo, reducción de solicitudes)
- [ ] Optimización de renderizado (por ejemplo, mejoras en las animaciones o UI)
- [ ] Refactorización
- [ ] Uso de corutinas
- [ ] Almacenamiento temporal (caching)

## Evidencias

Adjunta capturas de pantalla o registros que ayuden a verificar el problema identificado.
