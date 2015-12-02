(function() {
  var GiftsRecommenderCtrl;

  GiftsRecommenderCtrl = (function() {
    function GiftsRecommenderCtrl($log, GiftsRecommenderService) {
      this.$log = $log;
      this.GiftsRecommenderService = GiftsRecommenderService;
      this.$log.debug("constructing UserController");
      this.users = [];
      this.getAllUsers();
    }

    return GiftsRecommenderCtrl;

  })();

  controllersModule.controller('DemoController', function($scope) {
    $scope.images = [1, 2, 3, 4, 5, 6, 7, 8];
    return $scope.loadMore = function() {
      var i, last, _i;
      last = $scope.images[$scope.images.length - 1];
      for (i = _i = 1; _i <= 8; i = ++_i) {
        $scope.images.push(last + i);
      }
    };
  });

}).call(this);

//# sourceMappingURL=GiftsRecommenderCtrl.js.map
