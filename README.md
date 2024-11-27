## Usage

The application processes review data from `src/main/resources/reviews.csv` and generates sitter rankings,
which are then exported to a CSV file in the project root directory. I've saved an example output file in the
root directory called [`search_rankings_kirkum.csv`](./search_rankings_kirkum.csv).

## Example

To run the application and generate the output file with a timestamp:

1. **Open a terminal and navigate to the project directory**:

2. **Build the Docker image**:
    ```sh
    docker-compose build
    ```

3. **Run the Docker container**:
    ```sh
    docker-compose up
    ```

4. **Look for a file called [`search_rankings.csv`](./search_rankings.csv) in the root project directory**.

## Discussion Question

### How would you adjust the calculation and storage of search scores in a production application?

In a production application, the calculation and storage of search scores should be pre-computed and enhanced to include
geographic information. Here are the key adjustments I would make to ensure fast search results, even with a peak load
of 10 searches per second.

1. **Pre-computation and Caching**:
    - Pre-compute the search scores and store them in a fast-access data store like Redis. This reduces the need to
      compute scores on-the-fly, improving response times.
2. **Pagination and Caching**:
    - Implement pagination in the search results to limit the number of records returned per page. Cache the search
      results to reduce the load on the database and improve response times.
3. **Database Replication, Sharding, and Indexing**:
    - Use read replicas for the sitter database to distribute read queries and reduce the load on the primary database.
    - Place an index on the sitter id to speed up lookups when fetching sitter information for a page of sitters.
    - Shard the Sitter Scores in reddis based on a prefix of the sitters zip code. This will allow for a more even
      distribution of the scores across the redis cluster and allow for geographic based searches to find sitters within
      an owners region.
4. **Incremental Updates**:
    - Implement a mechanism to update scores incrementally as new data (e.g., reviews) comes in. This can be achieved
      using event-driven architecture with tools like Kafka to process and update scores in real-time. There can be
      several systems that might want to know about a new review (e.g., an email to the sitter when a review is posted).
      An event driven architecture would be a good choice in this scenario.
5. **Scalable Architecture**:
    - Design the system to be horizontally scalable. Use microservices to separate the score calculation logic from
      other parts of the application. Deploy these services in a containerized environment using Kubernetes for
      orchestration.
6. **Load Balancing and Auto-scaling**:
    - Implement load balancing to distribute incoming requests evenly across multiple instances of the service. Use
      auto-scaling to handle peak loads, ensuring the system can scale up or down based on demand.

### Partial System Architecture

```mermaid
graph TD;
    A[User] -->|"Search For Sitter"| B[UI];
    B -->|"Fetch Top Sitters"| C[Sitter Service];
    D(("Redis/NoSQL"));
    C -->|"Fetch Sitter Info"| E(("Database"));
    F[Score Calculation Service];
    F -->|Update Scores| D;
    C["Sitter Search Service"] -->|Fetch top Sitters by Zip Prefix| D;
    B -->|Submit Review| G[Review Service];
    D(("Sitter Scores Redis"))
    E(("Sitter DB"))	
	E(("DB"))
	G["Review Service"] --- n1["Kafka"]
	n1 ---|"Update Score"| F
	A --->|"Submit Review"| B