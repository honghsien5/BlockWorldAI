//Project Name: BlockWorld AI
//Author: Shien Hong
//Date: 12/4/14
//Description: BlockWorld AI is a project that is programmed to demonstrate the forward chaining process by calculating the path to reach from an initial state to the goal state.

package hw4;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

//class that store the data for each block
class Block{
	String name = "";
	
	Block(String name){
		this.name = name;
	}
	
	//constructor for cloning
	Block(Block dummy){
		this.name = dummy.name;
	}
	
}
//class that store the data for each rule
class Rule{
	String action;
	String name;
	String stackName;
	//constructor for pickup and putdown
	Rule(String action, String name){
		this.action = action;
		this.name = name;
		this.stackName="";
	}
	//constructor for stack and unstack
	Rule(String action, String name, String stackName){
		this.action = action;
		this.name = name;
		this.stackName = stackName;
	}
	
}
//class that construct and store the data for block world
class BlockWorld{
	
	
	private ArrayList<LinkedList<Block>> table;
	private Block hand;
	//generate the block set
	HashMap<String, Block> set;
	//constructor
	BlockWorld(){
		hand = null;
		table = new ArrayList<LinkedList<Block>>();
		set  = new HashMap<String, Block>();
	}
	//constructor for cloning
	BlockWorld(BlockWorld dummy){
		this.table = new ArrayList<LinkedList<Block>>();
		for(LinkedList<Block> stack : dummy.table){
			this.table.add(new LinkedList<Block>(stack));
		}
		
		if(dummy.hand==null){
			this.hand=null;
		}else{
			this.hand = new Block(dummy.hand);
		}
		this.set = new HashMap<String, Block> (dummy.set);
	}

	//create the block into the map
	void createBlock(String name){
		set.put(name,new Block(name));
	}
	//get the block from the set;
	Block getBlock(String name){
		return set.get(name);
	}
	//pickUp the block
	void pickUp(String stackName){
		for(LinkedList<Block>  stack : table){
			if(stack.size()==1 && stack.getLast().name.equals(stackName)){
				hand = stack.getLast();
				table.remove(stack);
				return;
			}
		}
	}
	//create a stack on the table
	void putDown(String name){
		LinkedList<Block> newStack = new LinkedList<Block>();
		newStack.add(getBlock(name));
		table.add(newStack);
		hand = null;
	}
	//add the target block on top of a stack
	void stack(String name, String stackName){
		for(LinkedList<Block>  stack : table){
			if(stack.getLast().name.equals(stackName)){
				stack.add(getBlock(name));
				hand = null;
				return;
			}
		}
	}
	//pick up a block from the stack
	void unstack(String name, String stackName){
		for(LinkedList<Block>  stack : table){
			if(stack.size()==1){

			}else {
				if (stack.get(stack.size() - 2).name.equals(stackName)) {
					hand = stack.getLast();
					stack.removeLast();
					return;
				}
			}
		}
	}
	
	//parse the description and perform task to blockworld
	void parseState(String description){
		int index = description.indexOf('(');
		//HE. Hand is empty
		if(index == -1){
			hand = null;
		}else{
			String action = description.substring(0,index);
			String name = description.substring(index+1,description.length()-1);
			switch (action){
				case "ON_TABLE":
					putDown(name);	
					break;
				case "CLEAR":
					break;
				case "ON":
					String[] names = name.split(",");
					stack(names[0],names[1]);
					break;
				case "HOLDING":
					hand=getBlock(name);
					break;
			}
		}
	}
	//return a string representation of the current state;
	String printState(){
		String description="";
		//iterate through each stack on the table
		for(LinkedList<Block>stack : table){
			Block lastBlock = null;
			//iterate through each block in the stack
			for(int i = 0 ; i < stack.size();i++){
				if(i==0){
					description+= "ON_TABLE(" + stack.get(0).name+"),\t";
				}else{
					description+= "ON(" + stack.get(i).name + ","+lastBlock.name+"),\t";
				}
				lastBlock=stack.get(i);
				
			}
			description+="CLEAR(" +lastBlock.name+"),\t";
		}
		//check hand
		if(hand ==null){
			description+="HE";
		}else{
			description+="HOLDING("+hand.name+")";
		}
		return description;
		
	}
	//return a list of description(string) for the current state
	LinkedList<String> getState(){
		
		LinkedList<String> description = new LinkedList<String>();
		//iterate through each stack on the table
		for(LinkedList<Block>stack : table){
			Block lastBlock = null;
			//iterate through each block in the stack
			for(int i = 0 ; i < stack.size();i++){
				if(i==0){
					description.add("ON_TABLE(" + stack.get(0).name+")");
				}else{
					description.add("ON(" + stack.get(i).name + ","+lastBlock.name+")");
				}
				lastBlock=stack.get(i);
				
			}
			description.add("CLEAR(" +lastBlock.name+")");
		}
		//check hand
		if(hand ==null){
			description.add("HE");
		}else{
			description.add("HOLDING("+hand.name+")");
		}
		return description;
		
	}
	//use the rule and return a new BlockWorld object
	BlockWorld useRule(Rule rule) {
		BlockWorld clone = new BlockWorld(this);
		switch(rule.action){
			case "PICKUP":
				clone.pickUp(rule.name);
				break;
			case "PUTDOWN":
				clone.putDown(rule.name);
				break;
			case "STACK":
				clone.stack(rule.name, rule.stackName);
				break;
			case "UNSTACK":
				clone.unstack(rule.name,rule.stackName);
				break;
		}
		
		return clone;
		
	}
	//apply the rule on this world
	void applyRule(Rule rule){
		switch(rule.action){
			case "PICKUP":
				pickUp(rule.name);
				break;
			case "PUTDOWN":
				putDown(rule.name);
				break;
			case "STACK":
				stack(rule.name, rule.stackName);
				break;
			case "UNSTACK":
				unstack(rule.name,rule.stackName);
				break;
		}
		
	}
	//apply all rules on the path list to the current world and return a new world
	BlockWorld applyPath(LinkedList<Rule> path) {
		BlockWorld newWorld = new BlockWorld(this);
		
		for(int i = 0 ; i < path.size();i++){
			newWorld.applyRule(path.get(i));
		}
		return newWorld;
	}

	//return a list of available move
	LinkedList<Rule> availableMove(){
		LinkedList<Rule> rules = new LinkedList<Rule>();
		if(hand==null){
			for(LinkedList<Block> stack: table){
				if(stack.size()==1){
					rules.add(new Rule("PICKUP",stack.getLast().name));
				}else{
					rules.add(new Rule("UNSTACK",stack.getLast().name,stack.get(stack.size()-2).name));
				}
			}
		}else{
			rules.add(new Rule("PUTDOWN",hand.name));
			for(LinkedList<Block> stack : table){
				rules.add(new Rule("STACK", hand.name, stack.getLast().name));
			}
		}
		
		return rules;
		
	}
	//see if this blockWorld is matching with the goal
	boolean matches(BlockWorld goal){
		Set<String> thisSet = new HashSet<String>(getState());
		Set<String> goalSet = new HashSet<String>(goal.getState());
		return thisSet.equals(goalSet);
	}
}

public class hw4 {
	static int count=0;
	static int countV2=0;
	//findPath that keep track of the path that has been done before
	static LinkedList<Rule> findPath (BlockWorld init, BlockWorld goal){
		if(init.matches(goal)){
			return new LinkedList<Rule>();
		}
		LinkedList<LinkedList<Rule>> queue = new LinkedList<LinkedList<Rule>>();
		Set<LinkedList<Rule>> traveledPath = new HashSet<LinkedList<Rule>>();

		LinkedList<Rule> firstMove = init.availableMove();
		for(Rule firstRule:firstMove){
			LinkedList<Rule> firstPath = new LinkedList<Rule>();
			firstPath.add(firstRule);
			queue.add(firstPath);
			traveledPath.add(firstPath);
		}
		while(!queue.isEmpty()){
			LinkedList<Rule> path = queue.pop();
			BlockWorld newWorld = init.applyPath(path);
			count++;
			if(newWorld.matches(goal)){
				return path;
			}
			
			LinkedList<Rule> ruleList = newWorld.availableMove();
			for (Rule new_rule : ruleList){
				LinkedList<Rule> new_path = new LinkedList<Rule>(path);
				new_path.add(new_rule);
				if(!traveledPath.contains(new_path)){
					queue.add(new_path);
					traveledPath.add(new_path);
				}

			}
		}
		return new LinkedList<Rule>();
		
		
	}
	//improved findPath algorithm that also keep track of the state that been done before
	static LinkedList<Rule> findPathV2 (BlockWorld init, BlockWorld goal){
		if(init.matches(goal)){
			return new LinkedList<Rule>();
		}
		LinkedList<LinkedList<Rule>> queue = new LinkedList<LinkedList<Rule>>();
		Set<LinkedList<Rule>> traveledPath = new HashSet<LinkedList<Rule>>();
		Set<LinkedList<String>> traveledWorld = new HashSet<LinkedList<String>>();
		LinkedList<Rule> firstMove = init.availableMove();
		for(Rule firstRule:firstMove){
			LinkedList<Rule> firstPath = new LinkedList<Rule>();
			firstPath.add(firstRule);
			queue.add(firstPath);
			traveledPath.add(firstPath);
		}
		while(!queue.isEmpty()){
			LinkedList<Rule> path = queue.pop();
			BlockWorld newWorld = init.applyPath(path);
			if(traveledWorld.contains(newWorld.getState())){
				continue;
			}else{
				traveledWorld.add(newWorld.getState());
			}
			countV2++;
			if(newWorld.matches(goal)){
				return path;
			}

			LinkedList<Rule> ruleList = newWorld.availableMove();
			for (Rule new_rule : ruleList){
				LinkedList<Rule> new_path = new LinkedList<Rule>(path);
				new_path.add(new_rule);
				if(!traveledPath.contains(new_path)){
					queue.add(new_path);
					traveledPath.add(new_path);
				}

			}
		}
		return new LinkedList<Rule>();


	}
	//print out the path that leads to the goal state
	static void printPath(BlockWorld init, LinkedList<Rule> path) {

		for(Rule rule : path){
			String buffer = "";
			buffer+=rule.action+ "(" + rule.name;
			if(!rule.stackName.equals("")){
				buffer+=", " +rule.stackName;
			}

			init.applyRule(rule);
			buffer+=")\t:" + init.printState();
			System.out.println(buffer);

		}
		System.out.println("\n"+path.size()+" Moves performed");
	}
	
	//main method
	public static void main(String[] args){
		if(args.length==0){
			System.out.println("No argument supplied.");
			return;
		}

		//reading the file
		File file;
		BufferedReader br;
		try {
			file = new File(args[0]);
			br = new BufferedReader(new FileReader(file));
		}catch(FileNotFoundException e){
			System.out.println("File does not exist.");
			return;
		}

		BlockWorld world = new BlockWorld();
		BlockWorld goal = new BlockWorld();
		int flag = 0;
		String line;

		System.out.println("Starting the parsing.....");
		//parse each line from the input file;
		try{
			while( (line = br.readLine())!= null) {
				if (line.matches("BLOCKS: *")) {
					flag = 1;
					continue;
				} else if (line.matches("INITIAL STATE: *")) {
					flag = 2;
					continue;
				} else if (line.matches("GOAL STATE: *")) {
					flag = 3;
					continue;
				}
				if (!line.equals("")) {
					switch (flag) {
						case 1:
							String[] blockNames = line.split(", ");
							for (String name : blockNames) {
								world.createBlock(name);
								goal.createBlock(name);
							}

							break;
						case 2:
							world.parseState(line);

							break;
						case 3:
							goal.parseState(line);
							break;
					}
				}

			}
		}catch(IOException ioe){
			System.out.println("Problem reading file.");
			return;
		}
		//catch incomplete file format
		if(flag!=3){
			System.out.println("There is a problem with the input file.");
			System.out.println("Please make sure the input file is in the correct format.");
			return;
		}
		BlockWorld worldV2 = new BlockWorld(world);
		System.out.println("Calculating path.....\n");
		//find optimal path
		LinkedList<Rule> finalPath = findPath(world,goal);
		printPath(world, finalPath);
		System.out.println(count+" states considered.");

		System.out.println("\nImproved version:");
		System.out.println("Calculating path.....\n");
		LinkedList<Rule> finalPathV2 = findPathV2(worldV2,goal);
		printPath(worldV2, finalPathV2);
		System.out.println(countV2+" states considered.");
	}
}
