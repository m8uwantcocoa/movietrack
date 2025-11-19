/**
 * @file script.js
 * @description JavaScript file for handling movie search and displaying staff picks.
 * @version 1.0.0
 * @author Emre
 */

const searchButton = document.getElementById('searchButton');
const movieInput = document.getElementById('movieInput');
const responseContainer = document.getElementById('responseContainer');

document.addEventListener('DOMContentLoaded', function() {
    var movies = ['Interstellar', 'Inception', 'The Dark Knight', 'Pulp Fiction', 'The Matrix', 'Fight Club', 'Forrest Gump', 'The Shawshank Redemption'];
    var delay = 550;
    var currentMovieIndex = 0;

    function type() {
        var txt = movies[currentMovieIndex].split('');
        var i = 0;
        $('.autoText').attr('placeholder', '');
        function typeChar() {
            if (i < txt.length) {
                $('.autoText').attr('placeholder', $('.autoText').attr('placeholder') + txt[i]);
                i++;
                setTimeout(typeChar, delay);
            } else {
                setTimeout(function() {
                    currentMovieIndex = (currentMovieIndex + 1) % movies.length;
                    type();
                }, 1000);
            }
        }
        typeChar();
    }
    type();
});

/**
 * @author Emre & Mojtaba & Aleksander
 */
function submitSearch(event, movieTitle) {
    if(event) {
        event.preventDefault();
    }

    const alertContainer = document.getElementById('alertContainer');
    if (!alertContainer) {
        console.error('Alert container not found');
        return;
    }

    alertContainer.innerHTML = '';
    const imdbIdPattern = /^tt\d{7,8}$/;
    const searchQuery = movieTitle || document.getElementById('searchInput')?.value || document.getElementById('headerSearchInput')?.value;

    console.log('Search Query:', searchQuery);

    const loadingAlert = document.createElement('div');
    loadingAlert.className = 'alert alert-info';
    loadingAlert.textContent = "We're searching for it!";
    alertContainer.appendChild(loadingAlert);

    const handleError = (message) => {
        alertContainer.innerHTML = '';
        const errorAlert = document.createElement('div');
        errorAlert.className = 'alert alert-danger';
        errorAlert.textContent = message;
        alertContainer.appendChild(errorAlert);
    };

    if(imdbIdPattern.test(searchQuery)) {
        fetch(`/api/movies/${encodeURIComponent(searchQuery)}`)
            .then(response => response.json())
            .then(data => {
                console.log('Response Data:', data);
                alertContainer.innerHTML = '';
                if (data && data.movie) {
                    const movieTitle = data.movie.Title;
                    localStorage.setItem('movieTitle', movieTitle);
                    const movie = data.movie;
                    localStorage.setItem('selectedMovie', JSON.stringify(movie));
                    window.location.href = 'Movie.html';
                } else {
                    handleError('No movie found with the given IMDb ID.');
                }
            })
            .catch((error) => {
                console.error('Error:', error);
                handleError('No internet connection or server error.');
            });
    } else {
        fetch(`/api/movies/${encodeURIComponent(searchQuery)}`)
            .then(response => response.json())
            .then(data => {
                console.log('Response Data:', data);
                alertContainer.innerHTML = '';
                if (data && data.movie && data.movie.length > 0) {
                    localStorage.setItem('searchResults', JSON.stringify(data.movie));
                    window.location.href = 'moviePicks.html';
                } else {
                    handleError('No movie or TV show found with the given title.');
                }
            })
            .catch((error) => {
                console.error('Error:', error);
                handleError('No internet connection or server error.');
            });
    }
}

/**
 * @author Emre & Mojtaba & Arian
 */
document.addEventListener('scroll', function() {
    const scrollPosition = window.scrollY;
    document.body.style.backgroundPositionY = `${scrollPosition * 0.0}px`;
});

document.addEventListener('DOMContentLoaded', function() {
    if (window.location.pathname.endsWith('tvPicks.html')) {
        localStorage.removeItem('selectedShow');
        localStorage.removeItem('searchResults');
        loadTopRatedTVShows();
    }
});

/**
 * @author Emre & Mojtaba & Aleksander
 */
function loadTopRatedTVShows() {
    fetch('/api/tv-shows')
        .then(response => response.json())
        .then(data => {
            const container = document.getElementById('tvShowContainer');
            container.innerHTML = '';

            data.forEach(show => {
                const col = document.createElement('div');
                col.className = 'col-md-3';
                const shortDescription = show.overview.split(' ').slice(0, 30).join(' ') + '...';
                col.innerHTML = `
                    <div class="card" onclick="submitSearch(event, \`${show.name.replace(/'/g, "\\'")}\`)">
                        <div class="card-inner">
                            <div class="card-front">
                                <img src="https://image.tmdb.org/t/p/w500${show.poster_path}" class="card-img-top" alt="${show.name}">
                                <div class="card-body">
                                    <h5 class="card-title">${show.name}</h5>
                                    <p class="card-text">Year: ${new Date(show.first_air_date).getFullYear()}</p>
                                    <p class="card-text">Rating: ${show.vote_average.toFixed(1)}</p>
                                </div>
                            </div>
                            <div class="card-back">
                                <div class="card-body">
                                    <h5 class="card-title">${show.name}</h5>
                                    <p class="card-text">${shortDescription}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                `;
                container.appendChild(col);
            });
        })
        .catch(error => console.error('Error fetching top-rated TV shows:', error));
}

document.addEventListener('DOMContentLoaded', function() {
    if (window.location.pathname.endsWith('tvPicks.html')) {
        loadTopRatedTVShows();
    }
});

document.addEventListener('DOMContentLoaded', function() {
    if (window.location.pathname.endsWith('mvPicks.html')) {
        loadTopRatedMovies();
    }
});

/**
 * @author Emre
 */
function loadTopRatedMovies() {
    fetch('/api/movies')
        .then(response => response.json())
        .then(data => {
            const container = document.getElementById('movieContainer');
            container.innerHTML = '';

            data.forEach(movie => {
                const col = document.createElement('div');
                col.className = 'col-md-3';
                const shortDescription = movie.overview.split(' ').slice(0, 30).join(' ') + '...';
                col.innerHTML = `
                    <div class="card" onclick="submitSearch(event, '${movie.title.replace(/'/g, "\\'")}')">
                        <div class="card-inner">
                            <div class="card-front">
                                <img src="https://image.tmdb.org/t/p/w500${movie.poster_path}" class="card-img-top" alt="${movie.title}">
                                <div class="card-body">
                                    <h5 class="card-title">${movie.title}</h5>
                                    <p class="card-text">Year: ${new Date(movie.release_date).getFullYear()}</p>
                                    <p class="card-text">Rating: ${movie.vote_average.toFixed(1)}</p>
                                </div>
                            </div>
                            <div class="card-back">
                                <div class="card-body">
                                    <h5 class="card-title">${movie.title}</h5>
                                    <p class="card-text">${shortDescription}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                `;
                container.appendChild(col);
            });
        })
        .catch(error => console.error('Error fetching top-rated movies:', error));
}

document.addEventListener('DOMContentLoaded', function() {
    const pathname = window.location.pathname;
    console.log('Pathname:', pathname);
    if (pathname.endsWith('/') || pathname.endsWith('index.html')){
        const staffPicks = ['Interstellar', 'Cars', 'Jurassic Park', 'Once Upon a time in Hollywood', 'War for the Planet of the Apes', 'Léon', 'Donnie Darko', 'django unchained'];
        loadStaffPicks(staffPicks);
    }
});

/**
 * @author Emre & Aleksander
 */
function loadStaffPicks(movies) {
    const carouselInner = document.getElementById('staffPicksCarousel');
    if (!carouselInner) {
        console.error('Element with ID "staffPicksCarousel" not found.');
        return;
    }

    const cards = carouselInner.querySelectorAll('.card');
    if (!cards.length) {
        console.error('No cards found in the carousel.');
        return;
    }

    movies.forEach((movie, index) => {
        if (index < cards.length) {
            fetch(`/api/movies/${encodeURIComponent(movie)}`)
                .then(response => response.json())
                .then(data => {
                    if (data.movie) {
                        console.log('Movie Details:', data.movie);
                        let movieDetails = data.movie[0];
                        const card = cards[index];
                        const img = card.querySelector('.card-img-top');
                        const title = card.querySelector('.card-title');
                        const rating = card.querySelector('.card-text');

                        img.src = `${movieDetails.Poster}`;
                        img.alt = movieDetails.Title;
                        title.textContent = movieDetails.Title;
                        title.style.fontWeight = '500';
                        rating.textContent = `IMDB: ${movieDetails.imdbRating}`;
                        rating.style.background = 'linear-gradient(to right, #230034, #00ffff)';
                        rating.style.color = '#ffffff';
                        rating.style.fontWeight = 'bold';
                        rating.style.border = '1px solid #000000';
                        rating.style.padding = '5px';
                        rating.style.display = 'inline-block';
                        rating.style.textAlign = 'center';
                        rating.style.fontSize = '12px';
                        rating.style.borderRadius = '5px';
                        rating.style.whiteSpace = 'nowrap';
                        rating.style.overflow = 'hidden';
                        rating.style.width = 'fit-content';
                        card.style.width = '200px';
                        card.style.height = '140px';
                        img.style.width = '90%';
                        img.style.height = '100px';
                        img.style.objectFit = 'contain';
                    }
                })
                .catch(error => console.error('Error fetching movie details:', error));
        }
    });
}