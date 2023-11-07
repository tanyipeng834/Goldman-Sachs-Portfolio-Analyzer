<div align="left">
  <img src="https://raw.githubusercontent.com/xuanli286/IS442-FRONTEND/d1a854cdcf681d22a726a4a4a6a22649b4135cb8/public/gs-logo.svg" alt="Logo" width="30%">
</div>
<br/>

## Table of Contents
<ol>
    <li>
        <a href="#frontend-repository">Frontend Repository</a>
    </li>
    <li>
        <a href="#about-the-project">About the Project</a>
    </li>
    <li>
        <a href="#features">Features</a>
    </li>
    <li>
        <a href="#uml-diagram">UML Diagram</a>
    </li>
    <li>
        <a href="#built-with">Built with</a>
        <ul>
            <li>
                <a href="#frontend">Frontend</a>
            </li>
            <li>
                <a href="#backend">Backend</a>
            </li>
        </ul>
    </li>
    <li>
        <a href="#getting-started">Getting Started</a>
        <ul>
            <li>
                <a href="#prerequisites">Prerequisites</a>
            </li>
            <li>
                <a href="#usage">Usage</a>
            </li>
        </ul>
    </li>
    <li>
        <a href="#acknowledgements">Acknowledgements</a>
    </li>
</ol>
<br/>

## Frontend Repository
https://github.com/xuanli286/IS442-FRONTEND

<p align="right">(<a href="#table-of-contents">back to top</a>)</p>

## About the Project
<p>
    Our application aims to help investment advisors and finance professionals analyze investment portfolios' performance and make informed investment decisions.
</p>
<p align="right">(<a href="#table-of-contents">back to top</a>)</p>


## Features
<ol>
    <li>Create, update and delete your portfolios</li>
    <li>View your profile information</li>
    <li>View other users' public portfolios</li>
    <li>
        Overall analysis of all your portfolios
        <ul>
            <li>Metrics - Total value, P&L, Buying Power and Daily P&L</li>
            <li>Ranking - By Porfolio value</li>
            <li>Performance - Monthly/Quarterly</li>
            <li>Market Exposure - By sector</li>
            <li>Country Exposure - By sector</li>
        </ul>
    </li>
    <li>
        Individual portfolio analysis (of your porfolios and community portfolios)
        <ul>
            <li>Portfolio Metrics - Portfolio value, P&L, Buying Power and Daily P&L</li>
            <li>Portfolio Stocks</li>
            <li>Market Exposure - By sector</li>
            <li>Fluctuations in price of each stock</li>
            <li>Company overview and financials</li>
        </ul>
    </li>
    <li>
        User logs that show
        <ul>
            <li>whenever you create, update or delete a portfolio</li>
            <li>when there's a change in quantity of stocks</li>
        </ul>
    </li>
</ol>

<p align="right">(<a href="#table-of-contents">back to top</a>)</p>

## UML Diagram
<div align="center">
	<img src="./images/uml.jpg" alt="uml-diagram" width="600" height="300">
</div>
<p align="right">(<a href="#table-of-contents">back to top</a>)</p>


## Built with

### Frontend
<ul>
    <li>
        <a href="https://vuejs.org">Vue.js</a>
    </li>
    <li>
        <a href="https://tailwindcss.com/">Tailwind CSS</a>
    </li>
    <li>
        <a href="https://pinia.vuejs.org/">Pinia</a>
    </li>
</ul>

### Backend
<ul>
    <li>
        <a href="https://www.java.com/en/">Java</a>
    </li>
    <li>
        <a href="https://maven.apache.org/index.html">Apache Maven</a>
    </li>
    <li>
        <a href="https://spring.io/projects/spring-boot">Spring Boot</a>
    </li>
    <li>
        <a href="https://github.com/microsoftarchive/redis/releases/tag/win-3.2.100">Redis</a>
    </li>
    <li>
        <a href="https://firebase.google.com/">Firebase</a>
    </li>
</ul>

<p align="right">(<a href="#table-of-contents">back to top</a>)</p>


## Getting Started

### Prerequisites
<ul>
    <li>
        <a href="https://nodejs.org/en">Node.js 18.17.1</a>
    </li>
    <li>
        <a href="https://maven.apache.org/install.html">Apache Maven 3.2.0</a>
    </li>
    <li>
        <a href="https://github.com/microsoftarchive/redis/releases/tag/win-3.2.100">Redis 3.2.1</a>
    </li>
</ul>

### Usage

1. Open the IS442-FRONTEND folder
2. Install required dependencies

```
npm install
```
3. Run the Frontend

```
npm run serve
```

4. Open the IS442-REST-API folder
5. Run the Backend

```
mvn spring-boot:run
```

<p align="right">(<a href="#table-of-contents">back to top</a>)</p>


## Acknowledgements
<ul>
    <li>Sioh Rui En, Regine</li>
    <li>Tan Li Xuan, Germaine</li>
    <li>Rachel Sng Yue Wei</li>
    <li>Mohammad Fadhli Bin Abdul Nassir</li>
    <li>Tan Yi Peng</li>
    <li>Low Xuanli</li>
</ul>

<p align="right">(<a href="#table-of-contents">back to top</a>)</p>