package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Packer {


  public static final String PROPERTY_LINE_SEPARATOR = System.getProperty("line.separator");

  private Packer() {
  }

  public static String pack(String filePath) throws APIException {
    List<Goal> goals=readFile(filePath);
    return evaluateGoals(goals);
  }

  private static String evaluateGoals(List<Goal> goals) {
    String response="";
    for (Goal goal:goals){
      System.out.println(goal);
      response+=checkGoal(goal)+ PROPERTY_LINE_SEPARATOR;
    }
    return response;
  }

  /**
   * The solution iterate over the possible solutions remain, if filter/max found one solution, it is added to solutions list and updated max weight value of the goal.
   * If no solution was found the check/while ends.
   * TODO. there is a possible upgrade using recursive solution but the cycles will be the same in this case.
   * @param goal
   * @return
   */
  private static String checkGoal(Goal goal) {
    Optional<Thing> op =Optional.empty();
    do {
      op = goal.getPosibleSolutions().stream().filter(x -> x.getWeight() <= goal.getMaxWeight())
              .max((thing1, t1) -> {
                if(thing1.getCost() - t1.getCost()!=0){
                  return (int) (thing1.getCost() - t1.getCost());
                }else{
                  return (int)(t1.getWeight() - thing1.getWeight());
                }
              });
      if(op.isPresent()){
        goal.getSolutions().add(op.get());
        goal.setMaxWeight(goal.getMaxWeight()-op.get().getWeight());
        goal.getPosibleSolutions().remove(op.get());
      }
    }while (op.isPresent());
    return fixResponse(goal);
  }

  /**
   * Response can be modified if apply, just need to change the map associated
   * @param goal
   * @return
   */
  private static String fixResponse(Goal goal) {
    String response = goal.getSolutions().stream()
            .map(thing -> Integer.toString(thing.getIndex()) )
            .collect(Collectors.joining(","));
    response=response.length()>0?response:"-";
    return response;
  }

  private static List<Goal> readFile(String fileName) throws APIException {
    if(!Files.exists(Paths.get(fileName))){
      throw  new APIException("File not found: "+fileName);
    }
    //read file into stream, try-with-resources
    List<Goal> goals=new ArrayList<>();
    try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

      stream.forEach(e -> {
        goals.add(evalLine(e));
      });

    } catch (IOException e) {
      e.printStackTrace();
    }
    validateBoundaries(goals);
    return goals;
  }

  /**
   * TODO: validate if apply to create enum or sub implementations of ApiExceptions for each case.  It just depends on the design
   * @param goals
   * @throws APIException
   */
  private static void validateBoundaries(List<Goal> goals) throws APIException {
    for (Goal g:goals ) {
      if (g.getMaxWeight()>100f){
        throw new APIException("Max Weight of package can not be higher than 100!, actual: "+g.getMaxWeight());
      }
      if (g.getMaxWeight()<0f){
        throw new APIException("Max Weight of package can not be lower that 0!, actual: "+g.getMaxWeight());
      }
      if( g.getPosibleSolutions().stream().filter(x -> x.getWeight() > 100f).count()>0){
        throw new APIException("Max Weight of an item can not be higher than 100!");
      }
      if( g.getPosibleSolutions().stream().filter(x -> x.getCost() > 100f).count()>0){
        throw new APIException("Max Cost of an item can not be higher than 100!");
      }

      if( g.getPosibleSolutions().stream().filter(x -> x.getWeight() < 0f).count()>0){
        throw new APIException("Max Weight of an item can not be lower than 100!");
      }
      if( g.getPosibleSolutions().stream().filter(x -> x.getCost() < 0f).count()>0){
        throw new APIException("Max Cost of an item can not be lower than 100!");
      }

    }
  }

  /**
   * TODO: This method can be modified in order to validate with REGEX if apply
   * @param line
   * @return
   */
  private static Goal evalLine(String line) {
    String []args=line.split(":");
    Goal g=new Goal();
    g.setMaxWeight(Float.parseFloat(args[0].trim()));

    String items[]=args[1].trim().split(" ");
    for(String item:items){
      item=item.substring(1,item.length()-1);
      item=item.replaceAll("€","");
      String params[]=item.split(",");
      g.getPosibleSolutions().add(new Thing(Integer.parseInt(params[0]),Float.parseFloat(params[1]),Float.parseFloat(params[2])));
    }
    return g;
  }
}
