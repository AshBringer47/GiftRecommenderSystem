class GiftsRecommenderCtrl

  constructor: (@$log, @GiftsRecommenderService) ->
    @$log.debug "constructing UserController"
    @users = []
    @getAllUsers()


controllersModule.controller('DemoController', ($scope) ->
  $scope.images = [1, 2, 3, 4, 5, 6, 7, 8]
  $scope.loadMore = ->
    last = $scope.images[$scope.images.length - 1];
    for i in [1..8]
      $scope.images.push(last + i)
    return
)

