class GiftsRecommenderService

  @headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}
  @defaultConfig = { headers: @headers }

  constructor: (@$log, @$http, @$q) ->
    @$log.debug "constructing UserService"



servicesModule.service('GiftsRecommenderService', ['$log', '$http', '$q', GiftsRecommenderService])