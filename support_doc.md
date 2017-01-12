SVG-Android supports part of the elements and attributes in the follow.

#SVG Elements

## g
```
<svg viewBox="0 0 95 50"
     xmlns="http://www.w3.org/2000/svg">
   <g stroke="green" fill="white" stroke-width="5">
     <circle cx="25" cy="25" r="15"/>
     <circle cx="40" cy="25" r="15"/>
     <circle cx="55" cy="25" r="15"/>
     <circle cx="70" cy="25" r="15"/>
   </g>
</svg>
```

## path
```
<svg width="100%" height="100%" viewBox="0 0 400 400"
     xmlns="http://www.w3.org/2000/svg">

  <path d="M 100 100 L 300 100 L 200 300 z"
        fill="orange" stroke="black" stroke-width="3" />
</svg>
```

## line
```
<svg width="120" height="120" viewBox="0 0 120 120"
    xmlns="http://www.w3.org/2000/svg">

  <line x1="20" y1="100" x2="100" y2="20"
      stroke-width="2" stroke="black"/>
</svg>
```

## rect
```
<svg width="120" height="120" viewBox="0 0 120 120"
    xmlns="http://www.w3.org/2000/svg">

  <rect x="10" y="10" width="100" height="100"/>
</svg>
```

## circle
```
<svg viewBox="0 0 200 200" xmlns="http://www.w3.org/2000/svg">
  <circle cx="100" cy="100" r="100"/>
</svg>
```

## polygon
```
<svg width="120" height="120" viewPort="0 0 120 120"
    xmlns="http://www.w3.org/2000/svg">

  <polygon points="60,20 100,40 100,80 60,100 20,80 20,40"/>
</svg>
```

## polyline
```
<svg width="120" height="120" xmlns="http://www.w3.org/2000/svg">
  <polyline fill="none" stroke="black"
      points="20,100 40,60 70,80 100,20"/>
</svg>
```

## ellipse
```
<svg width="120" height="120" viewBox="0 0 120 120"
    xmlns="http://www.w3.org/2000/svg">

  <ellipse cx="60" cy="60" rx="50" ry="25"/>
</svg>
```

## css style
```
<svg width="100%" height="100%" viewBox="0 0 100 100"
     xmlns="http://www.w3.org/2000/svg">
  <style>
    .a {
      fill: orange;
      stroke: black;
      stroke-width: 10px;
    }
  </style>

  <circle cx="50" cy="50" r="40" class="a"/>
</svg>
```
```
<svg width="100%" height="100%" viewBox="0 0 100 100"
     xmlns="http://www.w3.org/2000/svg">
  <style>
    circle {
      fill: orange;
      stroke: black;
      stroke-width: 10px;
    }
  </style>

  <circle cx="50" cy="50" r="40"/>
</svg>
```
```
<svg width="100%" height="100%" viewBox="0 0 100 100"
     xmlns="http://www.w3.org/2000/svg">
  <style>
    circle.a {
      fill: orange;
      stroke: black;
      stroke-width: 10px;
    }
  </style>

  <circle cx="50" cy="50" r="40" class="a"/>
</svg>
```

## css style in defs
```
<svg width="100%" height="100%" viewBox="0 0 100 100"
     xmlns="http://www.w3.org/2000/svg">
  <defs>
    <style>
       .a {
         fill: orange;
         stroke: black;
         stroke-width: 10px;
       }
     </style>
  </defs>

  <circle cx="50" cy="50" r="40" class="a"/>
</svg>
```

## use
```
<svg width="500" height="110" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">

  <g id="shape3">
      <rect x="0" y="0" width="50" height="50" />
  </g>

  <use xlink:href="#shape3" x="100" y="50" style="fill: #00ff00;"/>

</svg>
```

## symbol
```
<svg width="500" height="110" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">

  <symbol id="shape3">
      <rect x="0" y="0" width="50" height="50" />
  </symbol>

  <use xlink:href="#shape3" x="100" y="50" style="fill: #00ff00;"/>

</svg>
```


#SVG Attributes

- width
- height
- viewBox
- viewbox
- viewPort
- viewport
- id
- class
- transform
- style
- display
- stroke
- stroke-width
- stroke-opacity
- stroke-linejoin
- stroke-linecap
- stroke-miterlimit
- fill-opacity
- fill-rule
- x
- y
- cx
- cy
- r
- rx
- ry
- x1
- y1
- x2
- y2
- points
- fill
- d
- href
