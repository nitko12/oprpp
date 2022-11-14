package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDB {

    private List<StudentRecord> records;
    private Map<String, StudentRecord> jmbagIndex;

    public StudentDB(List<String> lines) {

        records = new ArrayList<>();
        jmbagIndex = new HashMap<>();

        for (String line : lines) {
            String[] splitLine = line.split("\t");

            try {
                String jmbag = splitLine[0];
                String lastName = splitLine[1];
                String firstName = splitLine[2];
                int finalGrade = Integer.parseInt(splitLine[3]);

                StudentRecord record = new StudentRecord(jmbag, lastName, firstName, finalGrade);

                if (jmbagIndex.containsKey(jmbag) || finalGrade < 1 || finalGrade > 5) {
                    throw new IllegalArgumentException("JMBAG " + jmbag + " vec postoji");
                }

                records.add(record);
                jmbagIndex.put(jmbag, record);

            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Neispravna linija: " + line);
            } catch (Exception e) {
                throw new IllegalArgumentException("Neispravna linija: " + line);
            }

        }
    }

    public StudentRecord forJMBAG(String jmbag) {
        return jmbagIndex.get(jmbag);
    }

    public List<StudentRecord> filter(IFilter filter) {
        ArrayList<StudentRecord> filteredRecords = new ArrayList<>();

        for (StudentRecord record : records) {
            if (filter.accepts(record)) {
                filteredRecords.add(record);
            }
        }

        return filteredRecords;
    }

}
