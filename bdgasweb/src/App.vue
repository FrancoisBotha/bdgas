<template>
  <div id="app">
      <div>
        <div class="container">
          <nav class="navbar navbar-expand-lg navbar-light bg-light ">
              <a class="navbar-brand" href="#">BDGAS</a>
              <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                  <span class="navbar-toggler-icon"></span>
              </button>
              <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                  <div class="navbar-nav ml-auto">
                      <a class="nav-item nav-link active" href="#">Home <span class="sr-only">(current)</span></a>
                      <a class="nav-item nav-link" href="/">Log Out</a>
                  </div>
              </div>
          </nav>
            <div class="row">
              <div class="col-3">
                <div class="card text-white bg-dark mb-3 mt-1 d-flex ">
                  <div class="card-header ">
                    <div class="mr-auto">Action </div>            
                  </div>
                  <div class="card-body text-dark bg-white" style="height:210px; padding: 0.25rem;">
                      <div class="input-group ml-0 mb-2">
                        <div class="btn-group dropright" >
                          <button type="button" class="btn btn-secondary dropdown-toggle"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Data Ingestion
                          </button>
                          <div class="dropdown-menu">
                            <a class="dropdown-item" href="#">Action</a>
                            <a class="dropdown-item" href="#">Another action</a>
                            <a class="dropdown-item" href="#">Something else here</a>
                          </div>
                        </div>
                      </div>             
                      <div class="input-group ml-0 mb-2">
                        <div class="btn-group dropright" >
                          <button type="button" class="btn btn-secondary dropdown-toggle"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Core Tests
                          </button>
                          <div class="dropdown-menu">
                            <a class="dropdown-item" href="#">Action</a>
                            <a class="dropdown-item" href="#">Another action</a>
                            <a class="dropdown-item" href="#">Something else here</a>
                          </div>
                        </div>
                      </div>  
                      <div class="input-group ml-0 mb-2">
                        <div class="btn-group dropright" >
                          <button type="button" class="btn btn-secondary dropdown-toggle"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Plugin Tests
                          </button>
                          <div class="dropdown-menu">
                            <a class="dropdown-item" href="#">Action</a>
                            <a class="dropdown-item" href="#">Another action</a>
                            <a class="dropdown-item" href="#">Something else here</a>
                          </div>
                        </div>
                      </div> 
                      <div class="input-group ml-0">
                        <div class="btn-group dropright" >
                          <button type="button" class="btn btn-secondary dropdown-toggle"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Data Display
                          </button>
                          <div class="dropdown-menu">
                            <a class="dropdown-item" href="#">Action</a>
                            <a class="dropdown-item" href="#">Another action</a>
                            <a class="dropdown-item" href="#">Something else here</a>
                          </div>
                        </div>
                      </div>      
                  </div>
                </div>
              </div>
              <div class="col-9 ml-0" style="padding-left: 1px;">
                <div class="card text-white bg-dark mb-3 mt-1">
                  <div class="card-header">
                    Help
                  </div>
                  <div class="card-body text-dark bg-white" style="height:210px; overflow-y: scroll">
                    <div style="">
                      <p class="text-justify">Ambitioni dedisse scripsisse iudicaretur. Cras mattis iudicium purus sit amet fermentum. Donec sed odio operae, eu vulputate felis rhoncus. Praeterea iter est quasdam res quas ex communi. At nos hinc posthac, sitientis piros Afros. Petierunt uti sibi concilium totius Galliae in diem certam indicere. Cras mattis iudicium purus sit amet fermentum.</p>
                      <br>
                      <p class="text-justify">Ambitioni dedisse scripsisse iudicaretur. Cras mattis iudicium purus sit amet fermentum. Donec sed odio operae, eu vulputate felis rhoncus. Praeterea iter est quasdam res quas ex communi. At nos hinc posthac, sitientis piros Afros. Petierunt uti sibi concilium totius Galliae in diem certam indicere. Cras mattis iudicium purus sit amet fermentum.</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col">
                  <div class="card text-white bg-dark mb-3 mt-1" >
                  <div class="card-header ">
                    Parameters
                  </div>
                  <div class="card-body text-dark bg-white" style="height:150px"> 
                  </div>
                </div>
              </div>
            </div>
        </div>
      </div>
  </div>
</template>
 
<script>
    export default {
    name: 'app',
    data () {
      return {
        msg: javaObj.message
      }
    }
}

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
 
<style>
#app {
 font-family: 'Avenir', Helvetica, Arial, sans-serif;
 -webkit-font-smoothing: antialiased;
 -moz-osx-font-smoothing: grayscale;
 text-align: center;
 color: #2c3e50;
 margin-top: 2px;
}
</style>
