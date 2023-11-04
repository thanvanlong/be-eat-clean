package com.tb.eatclean.controller;

import com.google.gson.Gson;
import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.repo.BillRepo;
import com.tb.eatclean.repo.CategoryRepo;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
class StatsCategory {
    String label;
    long revenue;
}

@AllArgsConstructor
class StatsCategoryWeek {
    String label;
    long revenue;
    LocalDate date;
}

@AllArgsConstructor
class ResponseExcel {
    String username;
    String food;
    long foodId;
    String category;
    int quantity;

}
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/stats")
public class StatsController {
    @Autowired
    private BillRepo billRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping("revenue")
    public ResponseEntity<ResponseDTO<long []>> revenues (@RequestParam("year") int year){
        List<Object[]> list = billRepo.statsRevenue(year);

        long[] monthData = new long[12];

        for (int i = 0; i < 12; i++) {
            monthData[i] = 0;
            for (Object[] m: list) {
                if (Objects.equals(m[0], i + 1)) {
                    monthData[i] = Long.parseLong(m[1].toString());
                    break;
                }
            }
        }
        return ResponseEntity.ok(new ResponseDTO<>(monthData, "200", "Success", true));
    }

    @GetMapping("category/year")
    public ResponseEntity<ResponseDTO<List<StatsCategory>>> categoryYear (@RequestParam("year") int year){
        List<Object[]> list = this.billRepo.statsCategory(year);

        List<Object[]> categories = this.categoryRepo.getCategorySelect();

        List<StatsCategory> statsCategories = new ArrayList<>();

        for(Object[] category: categories) {
            StatsCategory statsCategory = new StatsCategory(category[1].toString(), 0);


            for (Object[] c: list) {
                if(c[0] == category[0]) {
                    statsCategory.revenue = Long.parseLong(c[1].toString());
                }
            }

            statsCategories.add(statsCategory);
        }

        return ResponseEntity.ok(new ResponseDTO<>(statsCategories, "200", "Success", true));
    }

    @GetMapping("category/week")
    public ResponseEntity<ResponseDTO<List<StatsCategoryWeek>>> categoryWeek (){

        List<Object[]> categories = this.categoryRepo.getCategorySelect();

        List<StatsCategoryWeek> statsCategories = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        List<LocalDate> dateList = new ArrayList<>();
        for (int i = 1; i<= 7; i++) {
            dateList.add(currentDate.minusDays(i));
        }

        for (LocalDate date: dateList) {
            int day = date.getDayOfMonth();
            int month = date.getMonthValue();
            int year = date.getYear();

            List<Object[]> list = this.billRepo.statsCategory(year, month, day);

            for(Object[] category: categories) {
                StatsCategoryWeek statsCategory = new StatsCategoryWeek(category[1].toString(), 0, date);

                for (Object[] c: list) {
                    if(c[0] == category[0]) {
                        statsCategory.revenue = Long.parseLong(c[1].toString());
                    }
                }

                statsCategories.add(statsCategory);
            }
        }

        return ResponseEntity.ok(new ResponseDTO<>(statsCategories, "200", "Success", true));
    }

    @PostConstruct
    void test (){
        LocalDate currentDate = LocalDate.now();
        System.out.println(currentDate.getMonthValue());
        List<Object[]> objects = this.billRepo.statsEveryDay(currentDate.getYear());

        for(Object[] objects1: objects){
            System.out.println(objects1[0]); // name;
            System.out.println(objects1[1]); // category
            System.out.println(objects1[2]); // id
            System.out.println(objects1[3]); // name foods
            System.out.println(objects1[4]); // gia
            System.out.println(objects1[5]); // so luong
        }
    }


}
