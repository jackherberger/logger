import org.w3c.dom.Node;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy implements PathingStrategy {
     /*define closed list
          define open list
          while (true){
            Filtered list containing neighbors you can actually move to
            Check if any of the neighbors are beside the target
            set the g, h, f values
            add them to open list if not in open list
            add the selected node to close list
          return path*/

    public int computeHCost(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        List<Point> path = new LinkedList<>();
        List<AStarNode> open = new LinkedList<>();
        List<AStarNode> closed = new LinkedList<>();
        int startH = computeHCost(start, end);

        AStarNode startNode = new AStarNode(start, 0, startH, startH, null);


        open.add(startNode);
        AStarNode curr = startNode;

        while (!(withinReach.test(curr.pos, end))) {
            List<Point> neighbors = potentialNeighbors
                    .apply(curr.pos)
                    .filter(canPassThrough)
                    .collect(Collectors.toList());

            for (Point pos : neighbors) {
                int gCost = curr.g + 1;
                int hCost = computeHCost(pos, end);
                int fCost = gCost + hCost;
                AStarNode currNeighbor = new AStarNode(pos, gCost, hCost, fCost, curr);

                if (!(closed.contains(currNeighbor))){
                    if (!(open.contains(currNeighbor))){
                        open.add(currNeighbor);
                    }else if (currNeighbor.f < open.get(open.indexOf(currNeighbor)).f){
                        open.set(open.indexOf(currNeighbor), currNeighbor);
                        curr = currNeighbor.neighbor;
                    }
                }

            }

            open.remove(curr);
            closed.add(curr);

            if (!(open.size() > 0)){
                return new LinkedList<>();
            }

            Comparator comp = new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    AStarNode a1 = (AStarNode) o1;
                    AStarNode a2 = (AStarNode) o2;
                    return a1.f - a2.f;
                }
            };
            Collections.sort(open, comp);
            curr = open.get(0);
        }

        while (!(curr.equals(startNode))) {
            path.add(0, curr.pos);
            curr = curr.neighbor;
        }
        while (curr.equals(startNode) == false){
            path.add(0, curr.pos);
            curr = curr.neighbor;
        }
        return path;
        }


    }

