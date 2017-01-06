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

## style
