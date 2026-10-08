package com.recipeshare.config;

import com.recipeshare.entity.Recipe;
import com.recipeshare.entity.User;
import com.recipeshare.enums.RecipeStatus;
import com.recipeshare.enums.Role;
import com.recipeshare.repository.RecipeRepository;
import com.recipeshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository,
                           RecipeRepository recipeRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @org.springframework.beans.factory.annotation.Value("${app.admin.email:admin@recipeshare.com}")
    private String adminEmail;

    @org.springframework.beans.factory.annotation.Value("${app.admin.password:admin123}")
    private String adminPassword;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        User author = userRepository.findByEmail(adminEmail)
                .orElseGet(() -> {
                    User admin = User.builder()
                            .name("System Administrator")
                            .email(adminEmail)
                            .password(passwordEncoder.encode(adminPassword))
                            .role(Role.ROLE_ADMIN)
                            .build();
                    return userRepository.save(admin);
                });

        seedRecipe(author, "Butter Chicken",
                "[Category: Chicken] Classic Indian dish with tender chicken pieces cooked in a rich, creamy, and velvety tomato gravy seasoned with aromatic spices.",
                "500g Chicken, 1 cup Tomato Puree, 2 tbsp Butter, 1/2 cup Heavy Cream, 1 tbsp Ginger-Garlic Paste, 1 tsp Garam Masala, 1 tsp Kashmiri Red Chili Powder, 1 tsp Kasuri Methi, Salt to taste.",
                "1. Marinate chicken with yogurt, ginger-garlic paste, and spices for 30 minutes.\n2. Sear chicken pieces in a pan until lightly charred.\n3. Prepare gravy by simmering tomato puree, butter, and spices.\n4. Add chicken to gravy and cook for 15 mins.\n5. Stir in heavy cream and crushed kasuri methi before serving hot with naan.",
                "butter-chicken.jpg");

        seedRecipe(author, "Chicken Biryani",
                "[Category: Chicken] Aromatic Hyderabadi style basmati rice layered with marinated chicken, saffron, mint, and fried onions.",
                "500g Basmati Rice, 500g Chicken, 1 cup Fried Onions (Birista), 1/2 cup Curd, 2 tbsp Biryani Masala, Whole Spices (Cardamom, Cloves, Cinnamon), Saffron Milk, Fresh Mint & Coriander leaves.",
                "1. Marinate chicken with curd, spices, mint, and fried onions for 1 hour.\n2. Par-boil basmati rice with whole spices until 70% cooked.\n3. Layer marinated chicken and rice in a heavy pot.\n4. Top with saffron milk, ghee, and mint.\n5. Seal pot and cook on low heat (Dum) for 25 minutes.",
                "chicken-biryani.jpg");

        seedRecipe(author, "Chicken Tikka Masala",
                "[Category: Chicken] Grilled marinated chicken chunks served in a spiced, creamy tomato curry.",
                "500g Boneless Chicken, 1/2 cup Yogurt, 1 tbsp Lemon Juice, 1 cup Tomato Sauce, 1 Onion (chopped), 1/2 cup Cream, Cumin, Garam Masala, Chili Powder.",
                "1. Marinate chicken in yogurt and spices; grill on skewers or pan fry.\n2. Saute onions and ginger-garlic in butter, add tomato sauce and spices.\n3. Simmer sauce for 10 mins, add grilled chicken tikka pieces.\n4. Finish with cream and serve with garlic butter naan.",
                "chicken-tikka-masala.jpg");

        seedRecipe(author, "Chicken Curry",
                "[Category: Chicken] Homestyle Indian chicken curry cooked with onions, tomatoes, and traditional whole spices.",
                "600g Chicken, 2 Large Onions, 2 Tomatoes, 1 tbsp Ginger-Garlic Paste, Turmeric, Red Chili Powder, Coriander Powder, Mustard Oil, Fresh Cilantro.",
                "1. Heat mustard oil and saute whole spices and sliced onions till golden brown.\n2. Add ginger-garlic paste and chopped tomatoes; cook until oil separates.\n3. Add chicken pieces and spice powders; saute for 10 minutes.\n4. Add water, cover and simmer until chicken is tender. Garnish with cilantro.",
                "chicken-curry.jpg");

        seedRecipe(author, "Paneer Butter Masala",
                "[Category: Paneer] Soft paneer cubes smothered in a smooth, mildly sweet, and creamy tomato gravy.",
                "250g Paneer cubes, 3 Tomatoes, 10-12 Cashews, 2 tbsp Butter, 2 tbsp Cream, Garam Masala, Kasuri Methi, Sugar, Kashmiri Chili Powder.",
                "1. Blend boiled tomatoes and cashews into a smooth paste.\n2. Melt butter in a pan, add tomato-cashew puree and Kashmiri chili powder.\n3. Simmer until gravy thickens; add salt, a pinch of sugar, and kasuri methi.\n4. Toss in fresh paneer cubes and heavy cream. Cook for 2 mins.",
                "paneer-butter-masala.jpg");

        seedRecipe(author, "Kadai Paneer",
                "[Category: Paneer] Paneer and crunchy bell peppers tossed in a spicy, freshly ground kadai masala gravy.",
                "250g Paneer, 1 Green Capsicum, 1 Red Onion, 2 Tomatoes, Kadai Masala (Coriander seeds, Dried Red Chilies, Fennel), Ginger juliennes.",
                "1. Dry roast coriander seeds and red chilies; coarsely grind to make Kadai Masala.\n2. Saute cubed capsicum and onion in oil until crisp-tender.\n3. Prepare tomato gravy with kadai masala and ginger.\n4. Add paneer cubes and sauteed vegetables; toss well on high heat.",
                "kadai-paneer.jpg");

        seedRecipe(author, "Palak Paneer",
                "[Category: Paneer] Healthy and delicious cottage cheese cubes cooked in a vibrant green spinach puree.",
                "250g Paneer, 1 Large Bunch Spinach (Palak), 1 Onion, 1 Tomato, 1 tbsp Ginger-Garlic Paste, Green Chilies, Cumin Powder, Garam Masala, Cream.",
                "1. Blanch spinach in hot water and immediately cool in ice water; blend into smooth puree.\n2. Heat oil, saute cumin, onions, green chilies, and ginger-garlic paste.\n3. Add tomato puree and spices, then pour in palak puree.\n4. Simmer for 5 mins, add paneer cubes and finish with a swirl of cream.",
                "palak-paneer.jpg");

        seedRecipe(author, "Paneer Tikka",
                "[Category: Paneer] Tandoori marinated paneer cubes and veggies grilled to smoky perfection.",
                "300g Paneer (cubed), 1 Capsicum, 1 Onion, 1/2 cup Hung Curd, 1 tbsp Besan (Roasted), Chaat Masala, Mustard Oil, Lemon Juice.",
                "1. Whisk hung curd, roasted besan, mustard oil, and tandoori spices.\n2. Coat paneer cubes, onion, and capsicum pieces in marinade for 30 mins.\n3. Thread onto skewers and grill in oven or pan until edges char.\n4. Sprinkle chaat masala and lemon juice before serving.",
                "paneer-tikka.jpg");

        seedRecipe(author, "Chole Masala",
                "[Category: Vegetarian] Spicy and tangy North Indian chickpea curry infused with aromatic tea bag spices.",
                "2 cups Chickpeas (Chole), 2 Onions, 2 Tomatoes, 1 Tea Bag, Chole Masala Powder, Amchur (Mango Powder), Ginger juliennes, Ghee.",
                "1. Pressure cook soaked chickpeas with tea bag and salt until soft.\n2. Saute finely chopped onions and ginger-garlic paste until dark brown.\n3. Add tomato puree, chole masala, and amchur powder.\n4. Mix in boiled chickpeas with cooking liquor; simmer for 15 minutes.\n5. Temper with ghee, cumin, and ginger juliennes.",
                "chole-masala.jpg");

        seedRecipe(author, "Aloo Gobi",
                "[Category: Vegetarian] Classic dry stir-fry recipe of tender potatoes and cauliflower florets seasoned with cumin and turmeric.",
                "1 Medium Cauliflower (florets), 2 Potatoes (cubed), 1 Onion, 1 Tomato, 1 tsp Cumin Seeds, Turmeric Powder, Coriander Powder, Garam Masala.",
                "1. Heat oil and add cumin seeds; stir-fry potato cubes and cauliflower florets until light golden.\n2. Add chopped onions, ginger, and green chilies.\n3. Add tomatoes, turmeric, coriander powder, and salt.\n4. Cover and steam on low heat until vegetables are tender. Sprinkle garam masala.",
                "aloo-gobi.jpg");

        seedRecipe(author, "Veg Biryani",
                "[Category: Vegetarian] Fragrant basmati rice cooked with mixed vegetables, saffron, ghee, and exotic spices.",
                "2 cups Basmati Rice, 1 cup Mixed Veggies (Carrot, Beans, Peas, Potato), 1/2 cup Yogurt, Biryani Spices, Mint, Saffron Milk, Ghee.",
                "1. Par-boil basmati rice with whole spices.\n2. Cook mixed vegetables with curd, biryani masala, and mint.\n3. Layer cooked vegetables and rice in a pot.\n4. Drizzle saffron milk and ghee; cover and dum cook for 20 minutes.",
                "veg-biryani.jpg");

        seedRecipe(author, "Masala Dosa",
                "[Category: South Indian] Crisp golden rice-lentil crepe filled with spiced potato masala, served with coconut chutney and sambar.",
                "Dosa Batter (Fermented Rice & Urad Dal), 3 Boiled Potatoes, 1 Onion, 1 tsp Mustard Seeds, Curry Leaves, Turmeric, Green Chilies, Oil.",
                "1. Prepare potato filling by sauteing mustard seeds, curry leaves, onions, turmeric, and mashed potatoes.\n2. Pour dosa batter on a hot tawa and spread in concentric circles.\n3. Drizzle oil and cook until crisp and golden brown.\n4. Place potato masala in center, fold and serve with coconut chutney.",
                "masala-dosa.jpg");

        seedRecipe(author, "Veg Sandwich",
                "[Category: Snacks] Crispy toasted sandwich stuffed with fresh vegetables, green mint chutney, and melted cheese.",
                "4 Bread Slices, 1 Cucumber (sliced), 1 Tomato (sliced), 1 Potato (boiled), Green Mint Chutney, Butter, Sandwich Masala, Cheese Slices.",
                "1. Spread butter and green mint chutney on bread slices.\n2. Layer cucumber, tomato, boiled potato slices, and sprinkle sandwich masala.\n3. Top with a cheese slice and place second bread slice.\n4. Toast on griddle or grill until golden brown and crispy.",
                "veg-sandwich.jpg");

        seedRecipe(author, "Rajma Masala",
                "[Category: Vegetarian] Hearty North Indian red kidney bean curry cooked in thick onion-tomato gravy.",
                "2 cups Red Kidney Beans (Rajma), 2 Onions (pureed), 2 Tomatoes (pureed), 1 tbsp Ginger-Garlic Paste, Rajma Masala, Garam Masala, Ghee.",
                "1. Soak rajma overnight and pressure cook until soft.\n2. Heat ghee, saute onion paste until golden brown, then add ginger-garlic.\n3. Add tomato puree and spices; cook until oil separates.\n4. Add boiled rajma with water, mash a few beans to thicken gravy.\n5. Simmer for 20 mins and serve with steamed rice.",
                "rajma-masala.jpg");

        seedRecipe(author, "Garlic Noodles",
                "[Category: Chinese] Indo-Chinese style stir-fried noodles tossed with minced garlic, soy sauce, and crisp vegetables.",
                "200g Hakka Noodles, 2 tbsp Minced Garlic, 1/2 cup Spring Onions, 1 Capsicum, 1 tbsp Soy Sauce, 1 tbsp Chili Sauce, 1 tsp Vinegar, Sesame Oil.",
                "1. Boil noodles untill al dente, drain and toss with a drop of oil.\n2. Heat sesame oil in a wok, saute minced garlic until fragrant and light golden.\n3. Add sliced capsicum and spring onion whites; stir-fry on high heat.\n4. Add soy sauce, chili sauce, vinegar, and boiled noodles; toss well.\n5. Garnish with spring onion greens.",
                "garlic-noodles.jpg");
    }

    private void seedRecipe(User author, String title, String description, String ingredients, String instructions, String imageUrl) {
        Optional<Recipe> existingOpt = recipeRepository.findByTitle(title);
        if (existingOpt.isPresent()) {
            Recipe existing = existingOpt.get();
            boolean updated = false;
            if (existing.getStatus() != RecipeStatus.APPROVED) {
                existing.setStatus(RecipeStatus.APPROVED);
                updated = true;
            }
            if (imageUrl != null && !imageUrl.equals(existing.getImageUrl())) {
                existing.setImageUrl(imageUrl);
                updated = true;
            }
            if (updated) {
                recipeRepository.save(existing);
            }
            return;
        }

        Recipe recipe = Recipe.builder()
                .title(title)
                .description(description)
                .ingredients(ingredients)
                .instructions(instructions)
                .imageUrl(imageUrl)
                .status(RecipeStatus.APPROVED)
                .user(author)
                .build();

        recipeRepository.save(recipe);
    }
}
