import java.util.*;
import java.util.ArrayList;
import java.util.List;

class Person {
    String Nam;
    int id;
    int age;
    String occupation;
    List<Interest> interests;

    public Person(String Nam, int id, int age, String occupation) {
        this.Nam = Nam;
        this.id = id;
        this.age = age;
        this.occupation = occupation;
        this.interests = new ArrayList<>();
    }

    public void addInterest(Interest interest) {
        interests.add(interest);
    }
}

class Interest {
    String Name;
    List<String> Requirements;

    public Interest(String Name, List<String> Requirements) {
        this.Name = Name;
        this.Requirements = Requirements;
    }

    public boolean isSimilar(Interest other, int similarityThreshold) {
        int matchCount = 0;
        for (String req : this.Requirements) {
            if (other.Requirements.contains(req)) {
                matchCount++;
            }
        }
        return matchCount >= similarityThreshold;
    }
}

class InterestGraph {
    Map<Person, List<Interest>> PersonInterestMap;
    Map<Interest, List<Interest>> interestConnections;

    public InterestGraph() {
        PersonInterestMap = new HashMap<>();
        interestConnections = new HashMap<>();
    }

    public void addPerson(Person person) {
        PersonInterestMap.put(person, person.interests);
        for (Interest interest : person.interests) {
            interestConnections.putIfAbsent(interest, new ArrayList<>());
            for (Interest other : person.interests) {
                if (!interest.equals(other) && interest.isSimilar(other, 1)) {
                    interestConnections.get(interest).add(other);
                }
            }
        }
    }

    public List<Person> findSimilarPeople(Person person, int similarityThreshold) {
        List<Person> similarPeople = new ArrayList<>();
        for (Person other : PersonInterestMap.keySet()) {
            if (!person.equals(other)) {
                for (Interest interest : person.interests) {
                    for (Interest otherInterest : other.interests) {
                        if (interest.isSimilar(otherInterest, similarityThreshold)) {
                            similarPeople.add(other);
                            break;
                        }
                    }
                }
            }
        }
        return similarPeople;
    }

    public Map<Integer, List<Person>> findClusters(int similarityThreshold) {
        Map<Integer, List<Person>> clusters = new HashMap<>();
        Set<Person> visited = new HashSet<>();
        int clusterId = 0;

        for (Person person : PersonInterestMap.keySet()) {
            if (!visited.contains(person)) {
                List<Person> cluster = new ArrayList<>();
                Queue<Person> queue = new LinkedList<>();
                queue.add(person);
                visited.add(person);

                while (!queue.isEmpty()) {
                    Person current = queue.poll();
                    cluster.add(current);

                    for (Person neighbor : findSimilarPeople(current, similarityThreshold)) {
                        if (!visited.contains(neighbor)) {
                            queue.add(neighbor);
                            visited.add(neighbor);
                        }
                    }
                }

                clusters.put(clusterId++, cluster);
            }
        }
        return clusters;
    }

    public void plotClusterData(int maxX) {
        for (int x = 1; x <= maxX; x++) {
            Map<Integer, List<Person>> clusters = findClusters(x);
            System.out.println("Similarity threshold: " + x);
            System.out.println("Number of clusters: " + clusters.size());
            System.out.println("Cluster sizes: " + clusters.values().stream().mapToInt(List::size).toArray());
            System.out.println();
        }
    }
}



public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InterestGraph graph = new InterestGraph();

        while (true) {
            System.out.println("Press 1 for add\nPress 2 for show");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                // Adding a new person
                System.out.print("Nam: ");
                String Nam = scanner.nextLine();
                System.out.print("ID: ");
                int id = scanner.nextInt();
                System.out.print("Age: ");
                int age = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                System.out.print("Occupation: ");
                String occupation = scanner.nextLine();

                Person person = new Person(Nam, id, age, occupation);

                while (true) {
                    System.out.print("Interest (press 0 to stop): ");
                    String interestNam = scanner.nextLine();
                    if (interestNam.equals("0")) break;

                    System.out.print("Number of Requirements: ");
                    int numRequirements = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    List<String> Requirements = new ArrayList<>();
                    for (int i = 0; i < numRequirements; i++) {
                        System.out.print("Requirement: ");
                        Requirements.add(scanner.nextLine());
                    }

                    Interest interest = new Interest(interestNam, Requirements);
                    person.addInterest(interest);
                }

                graph.addPerson(person);
            } else if (choice == 2) {
                // Show clusters based on similarity threshold
                System.out.print("Enter similarity threshold (x): ");
                int x = scanner.nextInt();
                graph.plotClusterData(x);
            }
        }
    }
}
