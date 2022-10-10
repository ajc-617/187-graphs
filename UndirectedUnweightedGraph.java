package graph;
import java.util.ArrayList;
/**
 * This class implements general operations on a graph as specified by UndirectedGraphADT.
 * It implements a graph where data is contained in Vertex class instances.
 * Edges between verticies are unweighted and undirected.
 * A graph coloring algorithm determines the chromatic number. 
 * Colors are represented by integers. 
 * The maximum number of vertices and colors must be specified when the graph is instantiated.
 * You may implement the graph in the manner you choose. See instructions and course material for background.
 */
 
 public class UndirectedUnweightedGraph<T> implements UndirectedGraphADT<T> {
   // private class variables here.
   
   private int MAX_VERTICES;
   private int MAX_COLORS;
   private ArrayList<Vertex<T>> vertices;
   private int[][] adjacencyMatrix;

   
   /**
    * Initialize all class variables and data structures. 
   */   
   public UndirectedUnweightedGraph (int maxVertices, int maxColors){
      MAX_VERTICES = maxVertices;
      MAX_COLORS = maxColors; 
      vertices = new ArrayList<Vertex<T>>();
      adjacencyMatrix = new int[maxVertices][maxVertices];
   }

   /**
    * Add a vertex containing this data to the graph.
    * Throws Exception if trying to add more than the max number of vertices.
   */
   public void addVertex(T data) throws Exception {
    if (vertices.size() == MAX_VERTICES) {
      throw new Exception();
    }
    vertices.add(new Vertex<T>(data));
   }
   
   /**
    * Return true if the graph contains a vertex with this data, false otherwise.
   */   
   public boolean hasVertex(T data){
      for (Vertex<T> curVertex: vertices) {
        if (curVertex.getData().equals(data)) {
          return true;
        }
      }
      return false;
   } 

   /**
    * Add an edge between the vertices that contain these data.
    * Throws Exception if one or both vertices do not exist.
   */   
   public void addEdge(T data1, T data2) throws Exception{
      boolean containsData1 = false;
      boolean containsData2 = false;
      Vertex<T> data1Vertex = null;
      Vertex<T> data2Vertex = null;
      for (Vertex<T> curVertex: vertices) {
        if (curVertex.getData().equals(data1)) {
          containsData1 = true;
          data1Vertex = curVertex;
        }
        if (curVertex.getData().equals(data2)) {
          containsData2 = true;
          data2Vertex = curVertex;
        }
      }
      if (!(containsData1 && containsData2)) {
        throw new Exception();
      }
      int data1VertexIndex = vertices.indexOf(data1Vertex);
      int data2VertexIndex = vertices.indexOf(data2Vertex);
      adjacencyMatrix[data1VertexIndex][data2VertexIndex] = 1;
      adjacencyMatrix[data2VertexIndex][data1VertexIndex] = 1;
   }

   /**
    * Get an ArrayList of the data contained in all vertices adjacent to the vertex that
    * contains the data passed in. Returns an ArrayList of zero length if no adjacencies exist in the graph.
    * Throws Exception if a vertex containing the data passed in does not exist.
   */   
   public ArrayList<T> getAdjacentData(T data) throws Exception{
      boolean containsData = false;
      Vertex<T> dataVertex = null;
      for (Vertex<T> curVertex: vertices) {
        if (curVertex.getData().equals(data)) {
          containsData = true;
          dataVertex = curVertex;
          break;
        }
      }
      if (!containsData) {
        throw new Exception();
      }
      ArrayList<T> adjacentData = new ArrayList<T>();
      int indexOfData = vertices.indexOf(dataVertex);
      for (int i = 0; i < vertices.size(); i++) {
        if (adjacencyMatrix[indexOfData][i] == 1) {
          adjacentData.add(vertices.get(i).getData());
        }
      } 
      return adjacentData;
    }
   
   /**
    * Returns the total number of vertices in the graph.
   */   
   public int getNumVertices(){
      return vertices.size();
   }

   /**
    * Returns the total number of edges in the graph.
   */   
   public int getNumEdges(){
    int sumOfAdjMatrix = 0;
     for (int i = 0; i < vertices.size(); i++) {
       for (int j = 0; j < vertices.size(); j++) {
         if (adjacencyMatrix[i][j] == 1) {
           sumOfAdjMatrix++;
         }
       }
     }
     return sumOfAdjMatrix/2;
   }

   /**
    * Returns the minimum number of colors required for this graph as 
    * determined by a graph coloring algorithm.
    * Throws an Exception if more than the maximum number of colors are required
    * to color this graph.
   */   
   public int getChromaticNumber() throws Exception{
      int highestColorUsed = -1;
      int colorToUse = -1;
      for (Vertex<T> curVert: vertices) {
        if (curVert.getColor() == -1) {
          try {
            colorToUse = getColorToUse(curVert);
            curVert.setColor(colorToUse);
            highestColorUsed = colorToUse;
          }
          catch (Exception Ex) {
            throw new Exception();
          }
        }
      }
    return highestColorUsed + 1;
   }

   private int getColorToUse(Vertex<T> curVertex) throws Exception {
      int colorToUse = -1;
      boolean[] adjColorsUsed = new boolean[MAX_COLORS];
      ArrayList<Vertex<T>> adjVertsList = getAdjacentVertices(curVertex);
      for (Vertex<T> curAdjVert: adjVertsList) {
        if (curAdjVert.getColor() != -1) {
          int color = curAdjVert.getColor();
          adjColorsUsed[color] = true;
        }
      }
      boolean allColorsUsed = true;
      for (int i = 0; i < MAX_COLORS; i++) {
        if (adjColorsUsed[i] == false) {
          allColorsUsed = false;
          colorToUse = i;
          break;
        }
      }
      if (allColorsUsed) {
        throw new Exception("Error: All colors have been used, the Max number of colors has been exceeded");
      }
      else {
        return colorToUse;
      }
   }

   private ArrayList<Vertex<T>> getAdjacentVertices (Vertex<T> vertex) {
     ArrayList<Vertex<T>> adjacentVertices = new ArrayList<Vertex<T>>();
     try {
      for (T curData: getAdjacentData(vertex.getData())) {
        for (Vertex<T> curVertex: vertices) {
          if (curData.equals(curVertex.getData())) {
            adjacentVertices.add(curVertex);
            break;
          }
        }
      }
     } 
     catch (Exception Ex) {
        return null;
     }
     return adjacentVertices;
   }
}