(function() {
  var GiftsRecommenderService;

  GiftsRecommenderService = (function() {
    GiftsRecommenderService.headers = {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    };

    GiftsRecommenderService.defaultConfig = {
      headers: GiftsRecommenderService.headers
    };

    function GiftsRecommenderService($log, $http, $q) {
      this.$log = $log;
      this.$http = $http;
      this.$q = $q;
      this.$log.debug("constructing UserService");
    }

    return GiftsRecommenderService;

  })();

  servicesModule.service('GiftsRecommenderService', ['$log', '$http', '$q', GiftsRecommenderService]);

}).call(this);

//# sourceMappingURL=GiftsRecommenderService.js.map
