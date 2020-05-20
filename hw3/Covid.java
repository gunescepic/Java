import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Paths;
import java.util.Arrays;
import java.nio.file.Files;
import java.util.stream.Stream;
import java.io.IOException;
import java.text.DecimalFormat;
//Insterted libraries
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Comparator;

public class Covid
{
  // You can add your own variables between them.

  // You can add your own variables between them.

  // You must not change between them.
  private List<List<String>> rows;

  public Covid()
  {
    try
    {
      this.rows = Files
  				.lines(Paths.get("covid19.csv"))
  				.map(row -> Arrays.asList(row.split(",")))
  				.collect(Collectors.toList());
    }
    catch (IOException e)
    {
			e.printStackTrace();
		}
  }
  // You must not change between them.

  public void printOnlyCases(String location, String date)
  {
  	List<String> result = rows.stream()
  				.filter((x) -> location.equals(x.get(1)) && date.equals(x.get(2)))
  				.findAny()
  				.orElse(null);
  	int total_cases = Integer.parseInt(result.get(3));
  	int total_death = Integer.parseInt(result.get(5));
  	int cases = total_cases - total_death;
  	System.out.printf("Result: %d %n", cases);
  }

  public long getDateCount(String location)
  {
    long toReturn = rows.stream()
    				.filter(x -> location.equals(x.get(1)))
    				.count();

    return toReturn;
  }

  public int getCaseSum(String date)
  {
    int toReturn = 0;
    int num_of_cases = 0;
    List<List<String>> filtered = rows.stream()
    							.filter(x -> date.equals(x.get(2)))
    							.collect(Collectors.toList());
    List<Integer> filtered_int = filtered.stream()
    							.map(x -> Integer.parseInt(x.get(4)))
    							.collect(Collectors.toList());
    toReturn = filtered_int.stream()
    			.reduce(0, Integer::sum);
    return toReturn;
  }

  public long getZeroRowsCount(String location)
  {
    long toReturn = rows.stream()
    				.filter(x -> location.equals(x.get(1)) && x.get(3).equals("0") && x.get(4).equals("0") && x.get(5).equals("0") && x.get(6).equals("0"))
    				.count();
    return toReturn;
  }

  public double getAverageDeath(String location)
  {
  	//avg of new deaths for each date at given location
   	List<List<String>> filtered = rows.stream()
   								.filter(x -> location.equals(x.get(1)))
   								.collect(Collectors.toList());
   	List <Integer> ints = filtered.stream()
   						.map(x -> Integer.parseInt(x.get(6)))
   						.collect(Collectors.toList());
    double toReturn = ints.stream()
    				.mapToInt(x -> x)
    				.average()
    				.orElse(0.0);
    toReturn = Double.parseDouble(new DecimalFormat("##.00").format(toReturn));
    return toReturn;
  }

  public String getFirstDeathDayInFirstTenRows(String location)
  {
  	//first date that first death happened among first 10 dates.
    String toReturn;

    List<List<String>> dates = rows.stream()
    						.filter(x -> location.equals(x.get(1)))
    						.collect(Collectors.toList());
    if(dates.size() > 10){
    	dates = dates.stream()
    			.limit(10)
    			.collect(Collectors.toList());
    }
    List<String> dates2 = dates.stream()
    					.filter(x -> Integer.parseInt(x.get(6)) != 0)
    					.map(x -> x.get(2))
    					.collect(Collectors.toList());
    Optional<String> date = dates2.stream().findAny();
    if(!date.isPresent()){
    	toReturn = "Not Found";
    }
    else{
    	Optional<String> answer = dates2.stream().findFirst();
    	if (answer.isPresent()) { 
            toReturn = answer.get(); 
        } 
        else { 
            toReturn = "Not Found";
        } 
    }
    return toReturn;
  }

  public String[] getDateCountOfAllLocations()
  {
    String[] toReturn = null;
    List<String> codes = rows.stream()
    					.map(x -> (x.get(0)))
    					.distinct()
    					.collect(Collectors.toList());

    List<String> distinct_locations = rows.stream()
    								.map(x -> x.get(1))
    								.distinct()
    								.collect(Collectors.toList());

    List<String> date_counts = distinct_locations.stream()
    							.map(x -> Long.toString(getDateCount(x)))
    							.collect(Collectors.toList());
   	
   	Map<String,String> returns = new LinkedHashMap<>();
   	
   	for (int i= 0; i < codes.size(); i++) {
   		returns.put(codes.get(i),date_counts.get(i));
   	}
   	
   	List<String> toReturns = new ArrayList<String>();
    returns.forEach((x,y) -> toReturns.add(x + ": "+ y));
    toReturn = toReturns.stream().toArray(String[]::new);
    return toReturn;
  }

  public List<String> getLocationsFirstDeathDay()
  {
    List<String> toReturn = null;

    toReturn = rows.stream()
    			.filter(x -> x.get(6).equals(x.get(5)) && Integer.parseInt(x.get(6)) != 0)
    			.map(x -> x.get(1) +": "+ x.get(2))
    			.collect(Collectors.toList());

    return toReturn;
  }

  public String trimAndGetMax(String location, int trimCount)
  {
    String toReturn = null;
    List<List<String>> for_given_location = rows.stream()
    										.filter(x -> location.equals(x.get(1)))
    										.collect(Collectors.toList());

    List<List<String>> sorted_by_death = for_given_location.stream()
    										.sorted((a,b) -> Integer.parseInt(a.get(4)) - Integer.parseInt(b.get(4)))
    										.collect(Collectors.toList()); 
    
    int count = sorted_by_death.size();

    List<List<String>> trimmed_list = sorted_by_death.stream()
    									.limit(count - trimCount)
    									.skip(trimCount)
    									.collect(Collectors.toList());

    List<Integer> new_death_nums = trimmed_list.stream()
    								.map(x -> Integer.parseInt(x.get(6)))
    								.collect(Collectors.toList());

    Integer max_new_death = new_death_nums.stream()
    						.max(Comparator.comparing(Integer::valueOf))
    						.get();

    List<List<String>>  returns = trimmed_list.stream()
    								.filter(x -> Integer.toString(max_new_death)
    								.equals(x.get(6)))
    								.collect(Collectors.toList());

    List<String> date_deaths = returns.get(0);

    toReturn = date_deaths.get(2)+": "+date_deaths.get(6);

    return toReturn;
  }

  public List<List<String>> getOnlyCaseUpDays(String location)
  {
    List<List<String>> toReturn = null;

    toReturn = rows.stream()
    			.filter(x -> location.equals(x.get(1)) && Integer.parseInt(x.get(4)) > 0).collect(Collectors.toList());
    System.out.printf("Result: %d %n", toReturn.size());
    return toReturn;
  }

  public static void main(String[] args)
  {
  	Covid c = new Covid();
  	System.out.println("PrintOnlyCases:");
    c.printOnlyCases("Turkey", "2020-03-20");
    c.printOnlyCases("United States", "2020-02-25");
  	System.out.println("getDateCount:");
  	System.out.printf("%d %n",c.getDateCount("Turkey"));
  	System.out.printf("%d %n",c.getDateCount("Italy"));
  	System.out.println("getCaseSum:");
  	System.out.printf("%d %n",c.getCaseSum("2020-03-05"));
  	System.out.printf("%d %n",c.getCaseSum("2020-04-05"));
  	System.out.println("getZeroRowsCount:");
  	System.out.printf("%d %n", c.getZeroRowsCount("Turkey"));
  	System.out.printf("%d %n", c.getZeroRowsCount("Australia"));
  	System.out.println("getAverageDeath:");
  	System.out.printf("%f %n", c.getAverageDeath("Turkey"));
  	System.out.printf("%f %n", c.getAverageDeath("Italy"));
  	System.out.println("getFirstDeathDayInFirstTenRows:");
  	System.out.printf("%s %n", c.getFirstDeathDayInFirstTenRows("Turkey"));
  	System.out.printf("%s %n", c.getFirstDeathDayInFirstTenRows("Italy"));
  	System.out.printf("%s %n", c.getFirstDeathDayInFirstTenRows("Israel"));
  	System.out.printf("%s %n", c.getFirstDeathDayInFirstTenRows("Montenegro"));
  	System.out.println("getDateCountOfAllLocations:");
  	String[] getDateCountOfAllLocations = c.getDateCountOfAllLocations();
  	System.out.println(Arrays.toString(getDateCountOfAllLocations));
  	System.out.println("getLocationsFirstDeathDay:");
  	List<String> locations = c.getLocationsFirstDeathDay();
  	System.out.println(Arrays.toString(locations.toArray()));
  	System.out.println("trimAndGetMax:");
  	System.out.println(c.trimAndGetMax("Turkey",5));
  	System.out.println(c.getOnlyCaseUpDays("Turkey"));
  	System.out.println(c.getOnlyCaseUpDays("Aruba"));
  }
}
