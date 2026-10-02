package ru.glazgosmile.metmuseum.data.repository

import ru.glazgosmile.metmuseum.data.model.Artwork

object ArtworkRepository {

    private val artworks = listOf(
        Artwork(
            objectID = 1,
            title = "Bread Plate",
            artistDisplayName = "Unknown",
            objectDate = "1785–90",
            primaryImageSmall = "https://images.metmuseum.org/CRDImages/ad/web-large/DP208292.jpg",
            primaryImage = "https://images.metmuseum.org/CRDImages/ad/original/DP208292.jpg",
            medium = "Porcelain",
            dimensions = "Diam. 6 1/4 in. (15.9 cm)",
            creditLine = "Gift of Mr. and Mrs. Robert M. Olmsted, 1943",
            department = "The American Wing",
            culture = "Chinese, for American market",
            country = "China",
            classification = "Ceramics"
        ),
        Artwork(
            objectID = 2,
            title = "The Great Wave off Kanagawa",
            artistDisplayName = "Katsushika Hokusai",
            objectDate = "ca. 1830–32",
            primaryImageSmall = "https://images.metmuseum.org/CRDImages/as/web-large/DP130155.jpg",
            primaryImage = "https://images.metmuseum.org/CRDImages/as/original/DP130155.jpg",
            medium = "Woodblock print (nishiki-e); ink and color on paper",
            dimensions = "10 1/8 x 14 15/16 in. (25.7 x 37.9 cm)",
            creditLine = "Henry L. Phillips Collection, Bequest of Henry L. Phillips, 1939",
            department = "Asian Art",
            culture = "Japan",
            country = "Japan",
            classification = "Prints"
        ),
        Artwork(
            objectID = 3,
            title = "Self-Portrait",
            artistDisplayName = "Vincent van Gogh",
            objectDate = "1887",
            primaryImageSmall = "https://images.metmuseum.org/CRDImages/ep/web-large/DT1567.jpg",
            primaryImage = "https://images.metmuseum.org/CRDImages/ep/original/DT1567.jpg",
            medium = "Oil on canvas",
            dimensions = "17 1/4 x 14 1/8 in. (43.8 x 35.9 cm)",
            creditLine = "Bequest of Miss Adelaide Milton de Groot (1876–1967), 1967",
            department = "European Paintings",
            culture = "Dutch",
            country = "Netherlands",
            classification = "Paintings"
        ),
        Artwork(
            objectID = 4,
            title = "The Death of Socrates",
            artistDisplayName = "Jacques Louis David",
            objectDate = "1787",
            primaryImageSmall = "https://images.metmuseum.org/CRDImages/ep/web-large/DP-13139-001.jpg",
            primaryImage = "https://images.metmuseum.org/CRDImages/ep/original/DP-13139-001.jpg",
            medium = "Oil on canvas",
            dimensions = "51 x 77 1/4 in. (129.5 x 196.2 cm)",
            creditLine = "Catharine Lorillard Wolfe Collection, Wolfe Fund, 1931",
            department = "European Paintings",
            culture = "French",
            country = "France",
            classification = "Paintings"
        ),
        Artwork(
            objectID = 5,
            title = "Armor of Henry II of France",
            artistDisplayName = "Unknown",
            objectDate = "ca. 1555",
            primaryImageSmall = "https://images.metmuseum.org/CRDImages/aa/web-large/DP-12879-001.jpg",
            primaryImage = "https://images.metmuseum.org/CRDImages/aa/original/DP-12879-001.jpg",
            medium = "Steel, gold, silver, leather, textile",
            dimensions = "H. 73 1/2 in. (186.7 cm)",
            creditLine = "Harris Brisbane Dick Fund, 1939",
            department = "Arms and Armor",
            culture = "French",
            country = "France",
            classification = "Armor"
        ),
        Artwork(
            objectID = 6,
            title = "The Unicorn Rests in a Garden",
            artistDisplayName = "Unknown",
            objectDate = "1495–1505",
            primaryImageSmall = "https://images.metmuseum.org/CRDImages/cl/web-large/DP118065.jpg",
            primaryImage = "https://images.metmuseum.org/CRDImages/cl/original/DP118065.jpg",
            medium = "Wool warp with wool, silk, silver, and gilt wefts",
            dimensions = "Overall: 144 7/8 x 122 3/8 in. (368 x 310.8 cm)",
            creditLine = "Gift of John D. Rockefeller Jr., 1937",
            department = "The Cloisters",
            culture = "South Netherlandish",
            country = "Netherlands",
            classification = "Textiles"
        ),
        Artwork(
            objectID = 7,
            title = "Madame Cézanne in a Red Dress",
            artistDisplayName = "Paul Cézanne",
            objectDate = "ca. 1888–90",
            primaryImageSmall = "https://images.metmuseum.org/CRDImages/ep/web-large/DT1951.jpg",
            primaryImage = "https://images.metmuseum.org/CRDImages/ep/original/DT1951.jpg",
            medium = "Oil on canvas",
            dimensions = "45 3/4 x 35 1/8 in. (116.2 x 89.2 cm)",
            creditLine = "The Walter H. and Leonore Annenberg Collection, Gift of Walter H. and Leonore Annenberg, 1997",
            department = "European Paintings",
            culture = "French",
            country = "France",
            classification = "Paintings"
        ),
        Artwork(
            objectID = 8,
            title = "Tiffany Glass Window",
            artistDisplayName = "Louis Comfort Tiffany",
            objectDate = "ca. 1912",
            primaryImageSmall = "https://images.metmuseum.org/CRDImages/ad/web-large/DP-15802-001.jpg",
            primaryImage = "https://images.metmuseum.org/CRDImages/ad/original/DP-15802-001.jpg",
            medium = "Leaded glass",
            dimensions = "Diameter: 24 in. (61 cm)",
            creditLine = "Gift of Robert Koch, 1979",
            department = "The American Wing",
            culture = "American",
            country = "United States",
            classification = "Glass"
        )
    )

    fun getAllArtworks(): List<Artwork> = artworks

    fun getArtworkById(objectID: Int): Artwork? {
        return artworks.find { it.objectID == objectID }
    }
}