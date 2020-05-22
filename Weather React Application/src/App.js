import React, { useState } from 'react';
import logo from './logo.svg';
import './App.css';

const api = {
  key: '3374651b97eafa2915ed539ecdf3b09f',
  base: 'https://api.openweathermap.org/data/2.5/',
};

function App() {
  let [query, setQuery] = useState('');
  let [weather, setWeather] = useState({});
  let [isError, setIsError] = useState(false);
  let [cssClassName, setCSSClassName] = useState('app');
  // let cssClassName = 'app rain';

  const search = (evt) => {
    if (evt.key === 'Enter') {
      fetch(`${api.base}weather?q=${query}&units=metric&APPID=${api.key}`)
        .then((res) => res.json())
        .then((result) => {
          setWeather(result);
          setQuery('');
          setCSSClassName(getCSSClassName(result));
          // document.getElementById('container').className = cssClassName;
          // document.getElementById('footer').className = 'footer special';
          document.querySelectorAll('.footer').className = 'footer special';
        });
    }
  };

  const dateBuilder = (d) => {
    let months = [
      'January',
      'February',
      'March',
      'April',
      'May',
      'June',
      'July',
      'August',
      'September',
      'October',
      'November',
      'December',
    ];
    let days = [
      'Sunday',
      'Monday',
      'Tuesday',
      'Wednesday',
      'Thursday',
      'Friday',
      'Saturday',
    ];

    let day = days[d.getDay()];
    let date = d.getDate();
    let month = months[d.getMonth()];
    let year = d.getFullYear();

    return `${day} ${date} ${month} ${year}`;
  };

  let getCSSClassName = (weather) => {
    if (weather.weather == undefined) {
      console.log('err');
      setIsError(true);
      // document.querySelector('#error').innerHTML =
      //   'Invalid city name. Please correct';
      return cssClassName;
    }
    setIsError(false);
    if (!weather.weather[0].main) {
      return 'app';
    }

    console.log(weather.weather[0].main);

    switch (weather.weather[0].main.toLowerCase()) {
      case 'rain':
        return 'app rain';

      case 'haze':
        return 'app haze';

      case 'clouds':
        return 'app clouds';

      case 'mist':
        return 'app mist';

      case 'snow':
        return 'app snow';

      default:
        return 'app';
    }
  };

  return (
    <div className={cssClassName} id="container">
      <main>
        <div className="search-box">
          <input
            type="text"
            className="search-bar"
            placeholder="Type city name and hit enter"
            onChange={(e) => setQuery(e.target.value)}
            value={query}
            onKeyPress={search}
            autoFocus
          />
        </div>
        {weather.main ? (
          <div className="container">
            <div className="location-box">
              <div className="location">
                {weather.name}, {weather.sys.country}
              </div>
              <div className="date">{dateBuilder(new Date())}</div>
            </div>
            <div className="weather-box">
              <div className="temp">{Math.round(weather.main.temp)}°c</div>
              <div className="weather">{weather.weather[0].main}</div>
            </div>
            <footer class="footer">
              Developed with passion by Karthik | Email:
              <a href="mailto:aswamykarthik@gmail.com">
                aswamykarthik@gmail.com
              </a>
            </footer>
          </div>
        ) : (
          <div className="container">
            {isError ? (
              <div id="error">Invalid City Name. Please correct</div>
            ) : (
              ''
            )}

            <footer class="footer">
              Developed with passion by Karthik | Email:
              <a href="mailto:aswamykarthik@gmail.com">
                aswamykarthik@gmail.com
              </a>
            </footer>
          </div>
        )}
      </main>
    </div>
  );
}

export default App;
