import java.util.*;

public class SimpleRecommender {

    static Map<Integer, Map<Integer, Double>> userRatings = new HashMap<>();

    public static void main(String[] args) {

        addSampleData();

   
        for (int userId : userRatings.keySet()) {
            List<Integer> recommendations = recommendItems(userId, 2); // Top 2 items
            System.out.println("Recommendations for user " + userId + ": " + recommendations);
        }
    }

    private static void addSampleData() {
  
        userRatings.put(1, Map.of(101, 4.5, 102, 3.0));
        userRatings.put(2, Map.of(101, 2.0, 103, 4.0, 104, 4.5));
        userRatings.put(3, Map.of(102, 5.0, 103, 3.5));
        userRatings.put(4, Map.of(104, 3.0, 105, 4.0, 106, 2.5));
        userRatings.put(5, Map.of(101, 3.5, 105, 5.0, 106, 4.0));
    }

    private static List<Integer> recommendItems(int userId, int numRecommendations) {
        Map<Integer, Double> targetRatings = userRatings.get(userId);
        Map<Integer, Double> scores = new HashMap<>();
        Map<Integer, Integer> count = new HashMap<>();

        for (Map.Entry<Integer, Map<Integer, Double>> entry : userRatings.entrySet()) {
            int otherUserId = entry.getKey();
            if (otherUserId == userId) continue;

            Map<Integer, Double> otherRatings = entry.getValue();

            for (Map.Entry<Integer, Double> itemRating : otherRatings.entrySet()) {
                int itemId = itemRating.getKey();
                double rating = itemRating.getValue();

                if (targetRatings.containsKey(itemId)) continue; // Skip if already rated

                scores.put(itemId, scores.getOrDefault(itemId, 0.0) + rating);
                count.put(itemId, count.getOrDefault(itemId, 0) + 1);
            }
        }

     
        List<Map.Entry<Integer, Double>> avgList = new ArrayList<>();
        for (int itemId : scores.keySet()) {
            avgList.add(Map.entry(itemId, scores.get(itemId) / count.get(itemId)));
        }

        avgList.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        List<Integer> recommendations = new ArrayList<>();
        for (int i = 0; i < Math.min(numRecommendations, avgList.size()); i++) {
            recommendations.add(avgList.get(i).getKey());
        }

        return recommendations;
    }
}
