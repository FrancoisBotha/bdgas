<template>
    <div>
        <nav class="navbar navbar-expand-sm navbar-light bg-light ">
            <a class="navbar-brand" href="#">BDGAS</a>
            <div v-if="localMode">- Local Mode</div>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <ul class="navbar-nav ml-auto">
                    <router-link to="/data" tag="li" active-class="active" >
                    <a class="nav-item nav-link">Data</a>
                    </router-link>
                    <router-link to="/audit" tag="li" active-class="active">
                    <a class="nav-item nav-link">Audit</a>
                    </router-link>
                    <a class="nav-item nav-link" @click="onGoHome" href="#">Home <span class="sr-only">(current)</span></a>
                    <div>&nbsp;</div>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >
                            {{userName}}
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" @click="onLogOut" href="#">Log Out</a>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>       
    </div>
</template>

<script>

  import config from '../config';
  import { store } from '../store/store'

  export default {
    computed: {
      localMode() {
        return this.$store.getters.localMode
      },
      userName() {
        return this.$store.getters.userName
      },    
    },
    methods: {
        onGoHome() {
            window.location.href = config.HOME_URL + "/" 
                                 + store.getters.teamId + "/" 
                                 + store.getters.projectId + "/" 
                                 + store.getters.wpId ;
        },
        onLogOut() {
            window.location.href = config.LOGOUT_URL;
        }
    }   
  }

  /*Custom Javascript for Nav Button */
  //   Courtesy: 
  // https://github.com/gatsbyjs/gatsby/issues/3809
  document.addEventListener('DOMContentLoaded', function () {

    // Get all "navbar-burger" elements
    var $navbarBurgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);

    // Check if there are any navbar burgers
    if ($navbarBurgers.length > 0) {

      // Add a click event on each of them
      $navbarBurgers.forEach(function ($el) {
        $el.addEventListener('click', function () {

          // Get the target from the "data-target" attribute
          var target = $el.dataset.target;
          var $target = document.getElementById(target);

          // Toggle the class on both the "navbar-burger" and the "navbar-menu"
          $el.classList.toggle('is-active');
          $target.classList.toggle('is-active');

        });
      });
    }

  });

</script>