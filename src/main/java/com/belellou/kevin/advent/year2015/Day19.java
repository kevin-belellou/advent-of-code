package com.belellou.kevin.advent.year2015;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.util.ModifiableInteger;

import com.belellou.kevin.advent.generic.AbstractDaySolver;
import com.belellou.kevin.advent.generic.DisableTest;

@SuppressWarnings("unused")
public class Day19 extends AbstractDaySolver<Integer> {

    public Day19() {
        super(Day19.class);
    }

    private static void find(String medicine, String current, int currentIndex, ModifiableInteger maxIndex,
                             Map<String, List<String>> map) {
        maxIndex.setValue(Math.max(currentIndex, maxIndex.getValue()));
        if (currentIndex > 35) {
            System.out.println("currentIndex: " + currentIndex + ", Current: " + current);
        }

        if (current.length() > 40) {
            return;
        }

        String realCurrent = current.substring(currentIndex);
//        System.out.println("Real current: " + realCurrent);
        String prefix = current.substring(0, currentIndex);
//        System.out.println("Prefix: " + prefix);

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            if (!realCurrent.startsWith(key)) {
                continue;
            }

            for (String s : entry.getValue()) {
//                String newCurrent = s + realCurrent.substring(Math.min(s.length(), realCurrent.length()));
                String newCurrent = realCurrent.replaceFirst(key, s);
                String newMedicine = prefix + newCurrent;
//                System.out.println("newMedicine: " + newMedicine);

                if (newMedicine.equals(medicine)) {
                    System.out.println("Found");
                    throw new RuntimeException("Found");
                } else if (newMedicine.length() > medicine.length()) {
//                    System.out.println("Too long");
                    // Do nothing
                } else {
                    int endIndex = newMedicine.length();
                    while (!medicine.startsWith(newMedicine.substring(0, endIndex))) {
                        endIndex--;
                        if (endIndex == 0) {
                            break;
                        }
                    }
//                    System.out.println("endIndex: " + endIndex);

                    if (endIndex >= currentIndex) {
                        find(medicine, newMedicine, endIndex, maxIndex, map);
                    }
                }

//                Pattern.compile(Pattern.quote(entry.getKey()))
//                       .matcher(realCurrent)
//                       .results()
//                       .forEach(matchResult -> {
//                           int localIndex = matchResult.start();
//
//                           String newCurrent =
//                                   realCurrent.substring(0, localIndex) + s + realCurrent.substring(matchResult.end());
////                           System.out.println(newCurrent);
//                           String newMedicine = prefix + newCurrent;
//                           System.out.println("newMedicine: " + newMedicine);
//
//                           if (newMedicine.equals(medicine)) {
//                               System.out.println("Found");
//                               throw new RuntimeException("Found");
//                           } else if (newMedicine.length() > medicine.length()) {
//                               System.out.println("Too long");
//                           } else {
//                               int endIndex = newMedicine.length();
//                               while (!medicine.startsWith(newMedicine.substring(0, endIndex))) {
//                                   endIndex--;
//                                   if (endIndex == 0) {
//                                       break;
//                                   }
//                               }
//                               System.out.println("endIndex: " + endIndex);
//
//                               find(medicine, newMedicine, endIndex, map);
//                           }
//                       });
            }
        }
    }

    @Override
    protected Integer doSolveFirstStar(BufferedReader reader) {
        List<String> lines = new ArrayList<>(reader.lines().toList());

        String medicine = lines.removeLast();
        lines.removeLast();

        Set<String> newMolecules = new HashSet<>();

        for (String line : lines) {
            String[] split = line.split(" => ");
            String base = split[0];
            String output = split[1];

            int index = 0;
            while ((index = medicine.indexOf(base, index)) != -1) {
                newMolecules.add(medicine.substring(0, index) + output + medicine.substring(index + base.length()));
                index += base.length();
            }
        }

        return newMolecules.size();
    }

    @Override
    public Integer getFirstStarSolution() {
        return 535;
    }

    @Override
    protected Integer doSolveSecondStar(BufferedReader reader) {
        List<String> lines = new ArrayList<>(reader.lines().toList());

        String medicine = lines.removeLast();
        lines.removeLast();

        Map<String, List<String>> map = new HashMap<>();

        for (String line : lines) {
            String[] split = line.split(" => ");
            String base = split[0];
            String output = split[1];

            if (map.containsKey(base)) {
                map.get(base).add(output);
            } else {
                List<String> list = new ArrayList<>();
                list.add(output);
                map.put(base, list);
            }
        }

        String initialMolecule = "e";
        ModifiableInteger maxIndex = new ModifiableInteger(0);
        find(medicine, initialMolecule, 0, maxIndex, map);

        return 0;
    }

    @DisableTest
    @Override
    public Integer getSecondStarSolution() {
        return 1;
    }
}
