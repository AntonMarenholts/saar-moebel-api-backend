package de.saarland.moebel;

import de.saarland.moebel.model.Category;
import de.saarland.moebel.repository.CategoryRepository;
import de.saarland.moebel.service.TranslationService; // Импортируем сервис
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final TranslationService translationService; // Добавляем сервис

    public DataInitializer(CategoryRepository categoryRepository, TranslationService translationService) {
        this.categoryRepository = categoryRepository;
        this.translationService = translationService; // Инициализируем
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            System.out.println(">>>> Creating initial categories... <<<<");

            List<Category> initialCategories = List.of(
                    new Category("Küchen", "kitchens"),
                    new Category("Schlafzimmermöbel", "bedroom_furniture"),
                    new Category("Wohnzimmer", "living_rooms"),
                    new Category("Kindermöbel", "children_furniture"),
                    new Category("Flurmöbel", "hallways"),
                    new Category("Büromöbel", "office_furniture"),
                    new Category("Gartenmöbel", "garden_furniture"),
                    new Category("Badezimmermöbel", "bathroom_furniture"),
                    new Category("Schränke", "wardrobes"),
                    new Category("Stühle", "chairs"),
                    new Category("Esszimmermöbel", "dining_furniture"),
                    new Category("Kabinette", "cabinets"),
                    new Category("Polstermöbel", "upholstered_furniture")
            );

            // Переводим названия для каждой категории
            for (Category category : initialCategories) {
                String nameDe = category.getNameDe();
                category.setNameEn(translationService.translate(nameDe, "EN-US"));
                category.setNameFr(translationService.translate(nameDe, "FR"));
                category.setNameRu(translationService.translate(nameDe, "RU"));
                category.setNameUk(translationService.translate(nameDe, "UK"));
            }

            categoryRepository.saveAll(initialCategories);
            System.out.println(">>>> " + initialCategories.size() + " categories created. <<<<");
        }
    }
}