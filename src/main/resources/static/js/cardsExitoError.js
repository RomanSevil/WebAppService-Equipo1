body {
    --background-color: #18181B;
    --text-color: #A1A1AA;
    --card-background-color: rgba(255, 255, 255, 0.015);
    --card-border-color: rgba(255, 255, 255, 0.1);
    --card-box-shadow-1: rgba(0, 0, 0, 0.05);
    --card-box-shadow-1-y: 3px;
    --card-box-shadow-1-blur: 6px;
    --card-box-shadow-2: rgba(0, 0, 0, 0.1);
    --card-box-shadow-2-y: 8px;
    --card-box-shadow-2-blur: 15px;
    --card-label-color: #FFFFFF;
    --card-icon-color: #D4D4D8;
    --card-icon-background-color: rgba(255, 255, 255, 0.08);
    --card-icon-border-color: rgba(255, 255, 255, 0.12);
    --card-shine-opacity: 0.1;
    --card-shine-gradient: conic-gradient(from 205deg at 50% 50%, rgba(16, 185, 129, 0) 0deg, #10B981 25deg, rgba(52, 211, 153, 0.18) 295deg, rgba(16, 185, 129, 0) 360deg);
    --card-line-color: #2A2B2C;
    --card-tile-color: rgba(16, 185, 129, 0.05);
    --card-hover-border-color: rgba(255, 255, 255, 0.2);
    --card-hover-box-shadow-1: rgba(0, 0, 0, 0.04);
    --card-hover-box-shadow-1-y: 5px;
    --card-hover-box-shadow-1-blur: 10px;
    --card-hover-box-shadow-2: rgba(0, 0, 0, 0.3);
    --card-hover-box-shadow-2-y: 15px;
    --card-hover-box-shadow-2-blur: 25px;
    --card-hover-icon-color: #34D399;
    --card-hover-icon-background-color: rgba(52, 211, 153, 0.1);
    --card-hover-icon-border-color: rgba(52, 211, 153, 0.2);
    --blur-opacity: 0.01;
  }
  body.light {
    --background-color: #FAFAFA;
    --text-color: #52525B;
    --card-background-color: transparent;
    --card-border-color: rgba(24, 24, 27, 0.08);
    --card-box-shadow-1: rgba(24, 24, 27, 0.02);
    --card-box-shadow-1-y: 3px;
    --card-box-shadow-1-blur: 6px;
    --card-box-shadow-2: rgba(24, 24, 27, 0.04);
    --card-box-shadow-2-y: 2px;
    --card-box-shadow-2-blur: 7px;
    --card-label-color: #18181B;
    --card-icon-color: #18181B;
    --card-icon-background-color: rgba(24, 24, 27, 0.04);
    --card-icon-border-color: rgba(24, 24, 27, 0.1);
    --card-shine-opacity: 0.3;
    --card-shine-gradient: conic-gradient(from 225deg at 50% 50%, rgba(16, 185, 129, 0) 0deg, #10B981 25deg, #EDFAF6 285deg, #FFFFFF 345deg, rgba(16, 185, 129, 0) 360deg);
    --card-line-color: #E9E9E7;
    --card-tile-color: rgba(16, 185, 129, 0.08);
    --card-hover-border-color: rgba(24, 24, 27, 0.15);
    --card-hover-box-shadow-1: rgba(24, 24, 27, 0.05);
    --card-hover-box-shadow-1-y: 3px;
    --card-hover-box-shadow-1-blur: 6px;
    --card-hover-box-shadow-2: rgba(24, 24, 27, 0.1);
    --card-hover-box-shadow-2-y: 8px;
    --card-hover-box-shadow-2-blur: 15px;
    --card-hover-icon-color: #18181B;
    --card-hover-icon-background-color: rgba(24, 24, 27, 0.04);
    --card-hover-icon-border-color: rgba(24, 24, 27, 0.34);
    --blur-opacity: 0.1;
  }
  body.toggle .grid * {
    transition-duration: 0s !important;
  }
  .grid {
    display: grid;
    grid-template-columns: repeat(2, 240px);
    grid-gap: 32px;
    position: relative;
    z-index: 1;
  }
  .card {
    background-color: var(--background-color);
    box-shadow: 0px var(--card-box-shadow-1-y) var(--card-box-shadow-1-blur) var(--card-box-shadow-1), 0px var(--card-box-shadow-2-y) var(--card-box-shadow-2-blur) var(--card-box-shadow-2), 0 0 0 1px var(--card-border-color);
    padding: 56px 16px 16px 16px;
    border-radius: 15px;
    cursor: pointer;
    position: relative;
    transition: box-shadow 0.25s;
  }
  .card::before {
    content: '';
    position: absolute;
    inset: 0;
    border-radius: 15px;
    background-color: var(--card-background-color);
  }
  .card .icon {
    z-index: 2;
    position: relative;
    display: table;
    padding: 8px;
  }
  .card .icon::after {
    content: '';
    position: absolute;
    inset: 4.5px;
    border-radius: 50%;
    background-color: var(--card-icon-background-color);
    border: 1px solid var(--card-icon-border-color);
    backdrop-filter: blur(2px);
    transition: background-color 0.25s, border-color 0.25s;
  }
  .card .icon svg {
    position: relative;
    z-index: 1;
    display: block;
    width: 24px;
    height: 24px;
    transform: translateZ(0);
    color: var(--card-icon-color);
    transition: color 0.25s;
  }
  .card h4 {
    z-index: 2;
    position: relative;
    margin: 12px 0 4px 0;
    font-family: inherit;
    font-weight: 600;
    font-size: 14px;
    line-height: 2;
    color: var(--card-label-color);
  }
  .card p {
    z-index: 2;
    position: relative;
    margin: 0;
    font-size: 14px;
    line-height: 1.7;
    color: var(--text-color);
  }
  .card .shine {
    border-radius: inherit;
    position: absolute;
    inset: 0;
    z-index: 1;
    overflow: hidden;
    opacity: 0;
    transition: opacity 0.5s;
  }
  .card .shine:before {
    content: '';
    width: 150%;
    padding-bottom: 150%;
    border-radius: 50%;
    position: absolute;
    left: 50%;
    bottom: 55%;
    filter: blur(35px);
    opacity: var(--card-shine-opacity);
    transform: translateX(-50%);
    background-image: var(--card-shine-gradient);
  }
  .card .background {
    border-radius: inherit;
    position: absolute;
    inset: 0;
    overflow: hidden;
    -webkit-mask-image: radial-gradient(circle at 60% 5%, black 0%, black 15%, transparent 60%);
    mask-image: radial-gradient(circle at 60% 5%, black 0%, black 15%, transparent 60%);
  }
  .card .background .tiles {
    opacity: 0;
    transition: opacity 0.25s;
  }
  .card .background .tiles .tile {
    position: absolute;
    background-color: var(--card-tile-color);
    animation-duration: 8s;
    animation-iteration-count: infinite;
    opacity: 0;
  }
  .card .background .tiles .tile.tile-4, .card .background .tiles .tile.tile-6, .card .background .tiles .tile.tile-10 {
    animation-delay: -2s;
  }
  .card .background .tiles .tile.tile-3, .card .background .tiles .tile.tile-5, .card .background .tiles .tile.tile-8 {
    animation-delay: -4s;
  }
  .card .background .tiles .tile.tile-2, .card .background .tiles .tile.tile-9 {
    animation-delay: -6s;
  }
  .card .background .tiles .tile.tile-1 {
    top: 0;
    left: 0;
    height: 10%;
    width: 22.5%;
  }
  .card .background .tiles .tile.tile-2 {
    top: 0;
    left: 22.5%;
    height: 10%;
    width: 27.5%;
  }
  .card .background .tiles .tile.tile-3 {
    top: 0;
    left: 50%;
    height: 10%;
    width: 27.5%;
  }
  .card .background .tiles .tile.tile-4 {
    top: 0;
    left: 77.5%;
    height: 10%;
    width: 22.5%;
  }
  .card .background .tiles .tile.tile-5 {
    top: 10%;
    left: 0;
    height: 22.5%;
    width: 22.5%;
  }
  .card .background .tiles .tile.tile-6 {
    top: 10%;
    left: 22.5%;
    height: 22.5%;
    width: 27.5%;
  }
  .card .background .tiles .tile.tile-7 {
    top: 10%;
    left: 50%;
    height: 22.5%;
    width: 27.5%;
  }
  .card .background .tiles .tile.tile-8 {
    top: 10%;
    left: 77.5%;
    height: 22.5%;
    width: 22.5%;
  }
  .card .background .tiles .tile.tile-9 {
    top: 32.5%;
    left: 50%;
    height: 22.5%;
    width: 27.5%;
  }
  .card .background .tiles .tile.tile-10 {
    top: 32.5%;
    left: 77.5%;
    height: 22.5%;
    width: 22.5%;
  }
  @keyframes tile {
    0%, 12.5%, 100% {
      opacity: 1;
    }
    25%, 82.5% {
      opacity: 0;
    }
  }
  .card .background .line {
    position: absolute;
    inset: 0;
    opacity: 0;
    transition: opacity 0.35s;
  }
  .card .background .line:before, .card .background .line:after {
    content: '';
    position: absolute;
    background-color: var(--card-line-color);
    transition: transform 0.35s;
  }
  .card .background .line:before {
    left: 0;
    right: 0;
    height: 1px;
    transform-origin: 0 50%;
    transform: scaleX(0);
  }
  .card .background .line:after {
    top: 0;
    bottom: 0;
    width: 1px;
    transform-origin: 50% 0;
    transform: scaleY(0);
  }
  .card .background .line.line-1:before {
    top: 10%;
  }
  .card .background .line.line-1:after {
    left: 22.5%;
  }
  .card .background .line.line-1:before, .card .background .line.line-1:after {
    transition-delay: 0.3s;
  }
  .card .background .line.line-2:before {
    top: 32.5%;
  }
  .card .background .line.line-2:after {
    left: 50%;
  }
  .card .background .line.line-2:before, .card .background .line.line-2:after {
    transition-delay: 0.15s;
  }
  .card .background .line.line-3:before {
    top: 55%;
  }
  .card .background .line.line-3:after {
    right: 22.5%;
  }
  .card:hover {
    box-shadow: 0px 3px 6px var(--card-hover-box-shadow-1), 0px var(--card-hover-box-shadow-2-y) var(--card-hover-box-shadow-2-blur) var(--card-hover-box-shadow-2), 0 0 0 1px var(--card-hover-border-color);
  }
  .card:hover .icon::after {
    background-color: var(--card-hover-icon-background-color);
    border-color: var(--card-hover-icon-border-color);
  }
  .card:hover .icon svg {
    color: var(--card-hover-icon-color);
  }
  .card:hover .shine {
    opacity: 1;
    transition-duration: 0.5s;
    transition-delay: 0s;
  }
  .card:hover .background .tiles {
    opacity: 1;
    transition-delay: 0.25s;
  }
  .card:hover .background .tiles .tile {
    animation-name: tile;
  }
  .card:hover .background .line {
    opacity: 1;
    transition-duration: 0.15s;
  }
  .card:hover .background .line:before {
    transform: scaleX(1);
  }
  .card:hover .background .line:after {
    transform: scaleY(1);
  }
  .card:hover .background .line.line-1:before, .card:hover .background .line.line-1:after {
    transition-delay: 0s;
  }
  .card:hover .background .line.line-2:before, .card:hover .background .line.line-2:after {
    transition-delay: 0.15s;
  }
  .card:hover .background .line.line-3:before, .card:hover .background .line.line-3:after {
    transition-delay: 0.3s;
  }
  .day-night {
    cursor: pointer;
    position: absolute;
    right: 20px;
    top: 20px;
    opacity: 0.3;
  }
  .day-night input {
    display: none;
  }
  .day-night input + div {
    border-radius: 50%;
    width: 20px;
    height: 20px;
    position: relative;
    box-shadow: inset 8px -8px 0 0 var(--text-color);
    transform: scale(1) rotate(-2deg);
    transition: box-shadow 0.5s ease 0s, transform 0.4s ease 0.1s;
  }
  .day-night input + div:before {
    content: '';
    width: inherit;
    height: inherit;
    border-radius: inherit;
    position: absolute;
    left: 0;
    top: 0;
    transition: background-color 0.3s ease;
  }
  .day-night input + div:after {
    content: '';
    width: 6px;
    height: 6px;
    border-radius: 50%;
    margin: -3px 0 0 -3px;
    position: absolute;
    top: 50%;
    left: 50%;
    box-shadow: 0 -23px 0 var(--text-color), 0 23px 0 var(--text-color), 23px 0 0 var(--text-color), -23px 0 0 var(--text-color), 15px 15px 0 var(--text-color), -15px 15px 0 var(--text-color), 15px -15px 0 var(--text-color), -15px -15px 0 var(--text-color);
    transform: scale(0);
    transition: all 0.3s ease;
  }
  .day-night input:checked + div {
    box-shadow: inset 20px -20px 0 0 var(--text-color);
    transform: scale(0.5) rotate(0deg);
    transition: transform 0.3s ease 0.1s, box-shadow 0.2s ease 0s;
  }
  .day-night input:checked + div:before {
    background: var(--text-color);
    transition: background-color 0.3s ease 0.1s;
  }
  .day-night input:checked + div:after {
    transform: scale(1);
    transition: transform 0.5s ease 0.15s;
  }
  html {
    box-sizing: border-box;
    -webkit-font-smoothing: antialiased;
  }
  * {
    box-sizing: inherit;
  }
  *:before, *:after {
    box-sizing: inherit;
  }
  body {
    min-height: 100vh;
    display: flex;
    font-family: 'Inter', Arial;
    justify-content: center;
    align-items: center;
    background-color: var(--background-color);
    overflow: hidden;
  }
  body:before {
    content: '';
    position: absolute;
    inset: 0 -60% 65% -60%;
    background-image: radial-gradient(ellipse at top, #10B981 0%, var(--background-color) 50%);
    opacity: var(--blur-opacity);
  }
  body .twitter {
    position: fixed;
    display: block;
    right: 12px;
    bottom: 12px;
  }
  body .twitter svg {
    width: 32px;
    height: 32px;
    fill: #fff;
  }
  