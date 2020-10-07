package supporters;

import models.ChildPare;
import models.Point;
import models.Route;

import java.util.*;

public class GeneticAlgorithm {
    private final double widthScale;
    private final double heightScale;

    private ArrayList<Route> currentPopulation;
    private int mutationPercent;

    public GeneticAlgorithm(int popSize, ArrayList<Point> points, int startId, int mutationPercent, double widthScale, double heightScale) {
        this.mutationPercent = mutationPercent;
        this.widthScale = widthScale;
        this.heightScale = heightScale;

        ArrayList<Point> copyOfPoints = copyList(points);
        Point startPoint = copyOfPoints.remove(startId - 1);
        currentPopulation = createBasicPopulation(startPoint, copyOfPoints, popSize);
    }

    private ArrayList<Route> createBasicPopulation(Point start, ArrayList<Point> cities, int size) {
        ArrayList<Route> basicPopulation = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            Route newRoute;
            do {
                ArrayList<Point> citiesSeq = new ArrayList<>();
                citiesSeq.add(start);
                Collections.shuffle(cities);
                citiesSeq.addAll(copyList(cities));
                citiesSeq.add(start);

                newRoute = new Route(citiesSeq, widthScale, heightScale);
            } while (basicPopulation.contains(newRoute));

            basicPopulation.add(newRoute);
        }
        return basicPopulation;
    }

    public Route nextPopulationBestRoute() {
        Random random = new Random();
        PriorityQueue<Route> newPopulation = new PriorityQueue<>();

        while (newPopulation.size() < 50) {
            for (int i = 0; i < 200; i++) {
                int firstIndex = random.nextInt(currentPopulation.size());
                int secondIndex = random.nextInt(currentPopulation.size());
                if (firstIndex == secondIndex) continue;

                Route firstParent = currentPopulation.get(firstIndex);
                Route secondParent = currentPopulation.get(secondIndex);

                ChildPare childPare = crossover4p(firstParent, secondParent);
                if (!newPopulation.contains(childPare.firstChild)) newPopulation.add(childPare.firstChild);
                if (!newPopulation.contains(childPare.secondChild)) newPopulation.add(childPare.secondChild);
            }
        }

        Route bestRoute = newPopulation.peek();

        ArrayList<Route> newPopulationBest = new ArrayList<>(currentPopulation.size());
        for (int i = 0; i < currentPopulation.size(); i++) {
            newPopulationBest.add(newPopulation.poll());
        }

        currentPopulation = newPopulationBest;
        return bestRoute;
    }

    private ChildPare crossover4p(Route firstParent, Route secondParent) {
        int routeSize = firstParent.citySequence.size();

        Random random = new Random();
        int firstPartStart = 1 + random.nextInt(routeSize/2 - 1);
        int firstPartEnd = routeSize/2 + random.nextInt(routeSize/2 - 4);
        int secondPartStart = firstPartEnd + 2 + random.nextInt(routeSize - firstPartEnd - 4);
        int secondPartEnd = secondPartStart + 1 + random.nextInt(routeSize - secondPartStart - 2);

        ArrayList<Point> firstPart = new ArrayList<>(firstParent.citySequence.subList(firstPartStart, firstPartEnd + 1));
        ArrayList<Point> secondPart = new ArrayList<>(firstParent.citySequence.subList(secondPartStart, secondPartEnd + 1));
        ArrayList<Point> fromFirstParent = new ArrayList<>();
        fromFirstParent.addAll(copyList(firstPart));
        fromFirstParent.addAll(copyList(secondPart));

        LinkedList<Integer> positionsInSecond = new LinkedList<>();
        for (int i = 0; i < secondParent.citySequence.size(); i++) {
            for (Point point : fromFirstParent) {
                if (point.id == secondParent.citySequence.get(i).id) {
                    positionsInSecond.addLast(i);
                    break;
                }
            }
        }

        ArrayList<Point> firstChild = copyList(firstParent.citySequence);
        ArrayList<Point> secondChild = copyList(secondParent.citySequence);
        int posInFirst = firstPartStart;
        for (Point item : fromFirstParent) {
            int posInSecond = positionsInSecond.pollFirst();
            firstChild.set(posInFirst, secondChild.get(posInSecond));
            secondChild.set(posInSecond, item);

            if (++posInFirst == firstPartEnd + 1) posInFirst = secondPartStart;
        }

        mutation(firstChild);
        mutation(secondChild);

        return new ChildPare(new Route(firstChild, widthScale, heightScale), new Route(secondChild, widthScale, heightScale));
    }

    private void mutation(ArrayList<Point> child) {
        Random rand = new Random();
        int randInt = rand.nextInt(100);

        if (randInt < mutationPercent) {
            int firstCellId = 1 + rand.nextInt(child.size() - 2);
            int secondCellId;
            do {
                secondCellId = 1 + rand.nextInt(child.size() - 2);
            } while (firstCellId == secondCellId);

            Point firstCell = child.get(firstCellId);
            child.set(firstCellId, child.get(secondCellId));
            child.set(secondCellId, firstCell);
        }
    }


    private ArrayList<Point> copyList(ArrayList<Point> src) {
        ArrayList<Point> dest = new ArrayList<>();
        for (Point point : src) {
            dest.add((Point) point.clone());
        }
        return dest;
    }
}
