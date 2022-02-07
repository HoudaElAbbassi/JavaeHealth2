package utilities.distanceOfSearch;

/**
 * this class is a utility class and contains the static method calculating the distance of search
 * @Author Ahmed Agdmoun
 */
public class DistanceOfSearch {

    /**
     * this method calculates the approximate distance between the coordinates of two different addresses
     * @param lat1 represents the latitude value of the first address
     * @param lng1 represents the longitude value of the first address
     * @param lat2 represents the latitude value of the second address
     * @param lng2 represents the longitude value of the second address
     * @return the calculated distance as a double
     */
    public static double distance(double lat1, double lng1,
                                  double lat2, double lng2){
        double a = (lat1-lat2)*DistanceOfSearch.distancePerLatitude(lat1);
        double b = (lng1-lng2)*DistanceOfSearch.distancePerLongitude(lat1);
        return Math.sqrt(a*a+b*b);
    }

    /**
     * a helper method to calculate the distance only per longitude
     * @param lat represents the latitude
     * @return the calculated value
     */
    private static double distancePerLongitude(double lat){
        return 0.0003121092*Math.pow(lat, 4)
                +0.0101182384*Math.pow(lat, 3)
                -17.2385140059*lat*lat
                +5.5485277537*lat+111301.967182595;
    }

    /**
     * a helper method to calculate the distance only per latitude
     * @param lat represents the latitude
     * @return the calculated value
     */
    private static double distancePerLatitude(double lat){
        return -0.000000487305676*Math.pow(lat, 4)
                -0.0033668574*Math.pow(lat, 3)
                +0.4601181791*lat*lat
                -1.4558127346*lat+110579.25662316;
    }
}
