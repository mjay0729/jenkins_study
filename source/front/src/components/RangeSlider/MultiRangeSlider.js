import React, { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';

const MultiRangeSlider = (props) => {
  console.log(props, '@');
  const min = parseFloat(props.min || 0);
  const max = parseFloat(props.max || 100);
  const step = parseFloat(props.step || 5);
  let label =
    props.label === undefined || props.label === null ? true : props.label;

  label = label === 'false' || !label ? false : true;

  const [minValue, set_minValue] = useState(parseFloat(props.minValue || 25));
  const [maxValue, set_maxValue] = useState(parseFloat(props.maxValue || 75));
  const [barMin, set_barMin] = useState(((minValue - min) / (max - min)) * 100);
  const [barMax, set_barMax] = useState(((max - maxValue) / (max - min)) * 100);

  let refThis = useRef();

  let barBox = null;
  let startX = null;
  let barValue = 0;
  let bar = null;
  const onBarLeftClick = (e) => {
    let _minValue = minValue - step;
    if (_minValue < min) {
      _minValue = min;
    }
    set_minValue(_minValue);
    let _barMin = ((_minValue - min) / (max - min)) * 100;
    set_barMin(_barMin);
    triggerInput(_minValue, maxValue);
  };
  const onInputMinChange = (e) => {
    let _minValue = parseFloat(e.target.value);
    if (_minValue > maxValue - step) {
      _minValue = maxValue - step;
    }
    set_minValue(_minValue);
    let _barMin = ((_minValue - min) / (max - min)) * 100;
    set_barMin(_barMin);
    triggerInput(_minValue, maxValue);
  };
  const onLeftThumbMousedown = (e) => {
    startX = e.clientX;
    if (e.type === 'touchstart') {
      if (e.touches.length === 1) {
        startX = e.touches[0].clientX;
      } else {
        return;
      }
    }

    barValue = minValue;
    bar = e.target.parentNode;
    barBox = bar.getBoundingClientRect();
    document.addEventListener('mousemove', onLeftThumbMousemove);
    document.addEventListener('mouseup', onLeftThumbMouseup);
    document.addEventListener('touchmove', onLeftThumbMousemove);
    document.addEventListener('touchend', onLeftThumbMouseup);
    bar.classList.add('active');
  };
  const onLeftThumbMousemove = (e) => {
    let clientX = e.clientX;
    if (e.type === 'touchmove') {
      clientX = e.touches[0].clientX;
    }
    let dx = clientX - startX;
    let per = dx / barBox.width;
    let val = barValue + (max - min) * per;
    let strSetp = '' + step;
    let fixed = 0;
    strSetp.indexOf('.') >= 0 && (fixed = 2);
    val = parseFloat(val.toFixed(fixed));
    if (val < min) {
      val = min;
    } else if (val > maxValue - step) {
      val = maxValue - step;
    }
    set_minValue(val);
    let _barMin = ((val - min) / (max - min)) * 100;
    set_barMin(_barMin);
    triggerInput(val, maxValue);
  };
  const onLeftThumbMouseup = (e) => {
    document.removeEventListener('mousemove', onLeftThumbMousemove);
    document.removeEventListener('mouseup', onLeftThumbMouseup);
    document.removeEventListener('touchmove', onLeftThumbMousemove);
    document.removeEventListener('touchend', onLeftThumbMouseup);
    bar.classList.remove('active');
  };
  const onInnerBarLeftClick = (e) => {
    let _minValue = minValue + step;
    if (_minValue > maxValue - step) {
      _minValue = maxValue - step;
    }
    set_minValue(_minValue);
    let _barMin = ((_minValue - min) / (max - min)) * 100;
    set_barMin(_barMin);
    triggerInput(_minValue, maxValue);
  };
  const onInnerBarRightClick = (e) => {
    let _maxValue = maxValue - step;
    if (_maxValue < minValue + step) {
      _maxValue = minValue + step;
    }
    set_maxValue(_maxValue);
    let _barMax = ((max - _maxValue) / (max - min)) * 100;
    set_barMax(_barMax);
    triggerInput(minValue, _maxValue);
  };
  const onInputMaxChange = (e) => {
    let _maxValue = parseFloat(e.target.value);
    if (_maxValue < minValue + step) {
      _maxValue = minValue + step;
    }
    set_maxValue(_maxValue);
    let _barMax = ((max - _maxValue) / (max - min)) * 100;
    set_barMax(_barMax);
    triggerInput(minValue, _maxValue);
  };
  const onRightThumbMousedown = (e) => {
    startX = e.clientX;
    if (e.type === 'touchstart') {
      if (e.touches.length === 1) {
        startX = e.touches[0].clientX;
      } else {
        return;
      }
    }

    barValue = maxValue;
    bar = e.target.parentNode;
    barBox = bar.getBoundingClientRect();
    document.addEventListener('mousemove', onRightThumbMousemove);
    document.addEventListener('mouseup', onRightThumbMouseup);
    document.addEventListener('touchmove', onRightThumbMousemove);
    document.addEventListener('touchend', onRightThumbMouseup);
    bar.classList.add('active');
  };
  const onRightThumbMousemove = (e) => {
    let clientX = e.clientX;
    if (e.type === 'touchmove') {
      clientX = e.touches[0].clientX;
    }
    let dx = clientX - startX;
    let per = dx / barBox.width;
    let val = barValue + (max - min) * per;
    let strSetp = '' + step;
    let fixed = 0;
    strSetp.indexOf('.') >= 0 && (fixed = 2);
    val = parseFloat(val.toFixed(fixed));
    if (val < minValue + step) {
      val = minValue + step;
    } else if (val > max) {
      val = max;
    }
    set_maxValue(val);
    let _barMax = ((max - val) / (max - min)) * 100;
    set_barMax(_barMax);
    triggerInput(minValue, val);
  };
  const onRightThumbMouseup = (e) => {
    document.removeEventListener('mousemove', onRightThumbMousemove);
    document.removeEventListener('mouseup', onRightThumbMouseup);
    document.removeEventListener('touchmove', onRightThumbMousemove);
    document.removeEventListener('touchend', onRightThumbMouseup);
    bar.classList.remove('active');
  };
  const onBarRightClick = (e) => {
    let _maxValue = maxValue + step;
    if (_maxValue > max) {
      _maxValue = max;
    }
    set_maxValue(_maxValue);
    let _barMax = ((max - _maxValue) / (max - min)) * 100;
    set_barMax(_barMax);
    triggerInput(minValue, _maxValue);
  };

  const triggerInput = (minValue, maxValue) => {
    let retObj = { min, max, minValue, maxValue };
    props.onInput && props.onInput(retObj);
    props.onChange && props.onChange(retObj);
  };

  useEffect(() => {
    set_minValue(parseFloat(props.minValue));
    set_barMin(((minValue - min) / (max - min)) * 100);
    set_maxValue(parseFloat(props.maxValue));
    set_barMax(((max - maxValue) / (max - min)) * 100);
  }, [props.minValue, props.maxValue, minValue, min, maxValue, max]);

  return (
    <Wrapper className="multi-range-slider">
      <div className="bar" ref={refThis}>
        <div
          className="bar-left"
          style={{ width: barMin + '%' }}
          onClick={onBarLeftClick}
        ></div>
        <input
          className="input-type-range input-type-range-min"
          type="range"
          min={min}
          max={max}
          step={step}
          value={minValue}
          onInput={onInputMinChange}
        />
        <div
          className="thumb thumb-left"
          onMouseDown={onLeftThumbMousedown}
          onTouchStart={onLeftThumbMousedown}
        >
          <div className="min-value">{minValue}</div>
        </div>
        <div className="bar-inner">
          <div className="bar-inner-left" onClick={onInnerBarLeftClick}></div>
          <div className="bar-inner-right" onClick={onInnerBarRightClick}></div>
        </div>
        <input
          className="input-type-range input-type-range-max"
          type="range"
          min={min}
          max={max}
          step={step}
          value={maxValue}
          onInput={onInputMaxChange}
        />
        <div
          className="thumb thumb-right"
          onMouseDown={onRightThumbMousedown}
          onTouchStart={onRightThumbMousedown}
        >
          <div className="max-value">{maxValue}</div>
        </div>
        <div
          className="bar-right"
          style={{ width: barMax + '%' }}
          onClick={onBarRightClick}
        ></div>
      </div>
      {label && (
        <div className="label">
          <div className="label-min">{min}</div>
          <div className="label-max">{max}</div>
        </div>
      )}
      <div>
        <p className="fs-6 mb-0">Min value:{minValue.toLocaleString()}</p>
        <p className="fs-6">Man value:{maxValue.toLocaleString()}</p>
      </div>
    </Wrapper>
  );
};

export default MultiRangeSlider;

const Wrapper = styled.div`
  box-sizing: border-box;
  padding: 0px;
  margin: 0px;
  display: flex;
  position: relative;
  border: solid 1px gray;
  border-radius: 10px;
  padding: 40px 10px 10px 10px;
  box-shadow: 1px 1px 4px black;
  flex-direction: column;
  -webkit-touch-callout: none; /* iOS Safari */
  -webkit-user-select: none; /* Safari */
  -khtml-user-select: none; /* Konqueror HTML */
  -moz-user-select: none; /* Old versions of Firefox */
  -ms-user-select: none; /* Internet Explorer/Edge */
  user-select: none; /* Non-prefixed version, currently supported by Chrome, Edge,*/
  .bar {
    display: flex;
  }
  .bar * {
    transition: all 100ms;
  }
  .bar.active * {
    transition: none;
  }
  .bar-left {
    width: 25%;
    background-color: #f0f0f0;
    border-radius: 10px 0px 0px 10px;
    box-shadow: inset 0px 0px 5px black;
    padding: 4px 0px;
  }
  .bar-right {
    width: 25%;
    background-color: #f0f0f0;
    border-radius: 0px 10px 10px 0px;
    box-shadow: inset 0px 0px 5px black;
  }
  .bar-inner {
    background-color: rgb(255, 0, 0);
    display: flex;
    flex-grow: 1;
    flex-shrink: 1;
    justify-content: space-between;
    position: relative;
    border: solid 1px black;
    justify-content: space-between;
    box-shadow: inset 0px 0px 5px black;
  }
  .bar-inner-left {
    width: 50%;
  }
  .bar-inner-right {
    width: 50%;
  }
  .thumb {
    background-color: red;
    position: relative;
    z-index: 1;
    cursor: pointer;
  }
  .thumb::before {
    content: '';
    background-color: white;
    position: absolute;
    width: 20px;
    height: 20px;
    border: solid 1px black;
    box-shadow: 0px 0px 3px black, inset 0px 0px 5px gray;
    border-radius: 50%;
    z-index: 1;
    margin: -8px;
    cursor: pointer;
  }
  .input-type-range:focus + .thumb::after {
    content: '';
    position: absolute;
    top: -4px;
    left: -4px;
    width: 11px;
    height: 11px;
    z-index: 2;
    border-radius: 50%;
    border: dotted 1px black;
    box-shadow: 0px 0px 5px white, inset 0px 0px 10px black;
  }

  .thumb * {
    position: absolute;
    padding: 5px;
    bottom: 20px;
    left: -15px;
    font-size: 1px;
    text-align: center;
    background-color: #222;
    border-radius: 5px;
    color: white;
    display: none;
  }
  .thumb:active * {
    display: block;
  }
  .input-type-range {
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
    opacity: 0;
    pointer-events: none;
  }

  .ruler {
    margin: 10px 0px -5px 0px;
    display: flex;
    /* display: none; */
  }
  .ruler .ruler-rule {
    border-left: solid 1px;
    border-bottom: solid 1px;
    display: flex;
    flex-grow: 1;
    flex-shrink: 1;
    padding: 5px 0px;
  }

  .ruler .ruler-rule:last-child {
    border-right: solid 1px;
  }

  .label {
    display: flex;
    justify-content: space-between;
    padding: 0px;
    margin-top: 10px;
    margin-bottom: -20px;
    /* display: none; */
  }
  .label-min {
    font-size: 80%;
  }
  .label-max {
    font-size: 80%;
  }
`;
