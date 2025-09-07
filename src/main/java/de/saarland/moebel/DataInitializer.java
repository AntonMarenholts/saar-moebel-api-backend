package de.saarland.moebel;

import de.saarland.moebel.model.Category;
import de.saarland.moebel.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DataInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            System.out.println(">>>> Creating initial categories... <<<<");

            List<Category> initialCategories = List.of(
                    new Category("Кухни", "kitchens"),
                    new Category("Мебель для спальни", "bedroom_furniture"),
                    new Category("Гостиные", "living_rooms"),
                    new Category("Детская мебель", "children_furniture"),
                    new Category("Мебель для прихожей", "hallways"),
                    new Category("Офисная мебель", "office_furniture"),
                    new Category("Садовая мебель", "garden_furniture"),
                    new Category("Мебель для ванной", "bathroom_furniture"),
                    new Category("Шкафы", "wardrobes"),
                    new Category("Стулья", "chairs"),
                    new Category("Обеденная мебель", "dining_furniture"),
                    new Category("Кабинеты", "cabinets"),
                    new Category("Мягкая мебель", "upholstered_furniture")
            );

            categoryRepository.saveAll(initialCategories);
            System.out.println(">>>> " + initialCategories.size() + " categories created. <<<<");
        }
    }
}