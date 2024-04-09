>[!NOTE]
> Currently under construction will update when app is done

### Idea: Recipe Management API

**Description:**
This REST API allows users to manage recipes. Users can create, read, update, and delete recipes. Each recipe will have a title, description, list of ingredients, cooking instructions, and optionally, tags for categorization.

**Endpoints:**

1. **Create a Recipe**
   - Endpoint: POST /recipes
   - Request Body: JSON containing recipe details (title, description, ingredients, instructions, tags)
   - Response: Newly created recipe object with ID

2. **Get All Recipes**
   - Endpoint: GET /recipes
   - Response: JSON array containing all recipes

3. **Get Recipe by ID**
   - Endpoint: GET /recipes/{id}
   - Response: JSON object containing recipe details

4. **Update a Recipe**
   - Endpoint: PUT /recipes/{id}
   - Request Body: JSON containing updated recipe details (title, description, ingredients, instructions, tags)
   - Response: Updated recipe object

5. **Delete a Recipe**
   - Endpoint: DELETE /recipes/{id}
   - Response: Success message or HTTP status code

**Additional Endpoints:**

6. **Search Recipes by Title or Tag**
   - Endpoint: GET /recipes/search?query={query}
   - Response: JSON array containing recipes matching the search query

7. **Add Ingredient to a Recipe**
   - Endpoint: POST /recipes/{id}/ingredients
   - Request Body: JSON containing ingredient details
   - Response: Updated recipe object with added ingredient

8. **Remove Ingredient from a Recipe**
   - Endpoint: DELETE /recipes/{id}/ingredients/{ingredientId}
   - Response: Updated recipe object with removed ingredient

**Example Usage:**

- Users can create and manage their recipe collections using the API.
- Frontend applications or other services can utilize the API to search for recipes based on titles, tags, or ingredients.

**Benefits:**

- Offers practical experience in building a CRUD API with search functionality.
- Provides exposure to MongoDB's array operations for managing nested data structures.
- Allows for the exploration of reactive programming concepts with Spring Boot.
