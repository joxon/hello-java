SELECT sales_year, CONCAT(rep_first_name, ' ', rep_last_name) AS rep_name, sales_total,
    FIRST_VALUE(CONCAT(rep_first_name, ' ', rep_last_name))
        OVER (PARTITION BY sales_year ORDER BY sales_total DESC)
        AS highest_sales,
	NTH_VALUE(CONCAT(rep_first_name, ' ', rep_last_name), 2)
        OVER (PARTITION BY sales_year ORDER BY sales_total DESC
        RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING)
        AS second_highest_sales,
	LAST_VALUE(CONCAT(rep_first_name, ' ', rep_last_name))
        OVER (PARTITION BY sales_year ORDER BY sales_total DESC
        RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING)
        AS lowest_sales
FROM sales_totals JOIN sales_reps ON sales_totals.rep_id = sales_reps.rep_id;

SELECT rep_id, sales_year, sales_total AS current_sales,
    LAG(sales_total, 1, 0) OVER (PARTITION BY rep_id ORDER BY sales_year)
        AS last_sales,
    Sales_total - LAG(sales_total, 1, 0)
        OVER (PARTITION BY rep_id ORDER BY sales_year) AS 'change'
FROM sales_totals;

SELECT sales_year, rep_id, sales_total,
    PERCENT_RANK() OVER (PARTITION BY sales_year ORDER BY sales_total)
        AS pct_rank,
    CUME_DIST() OVER (PARTITION BY sales_year ORDER BY sales_total)
        AS 'cume_dist'
FROM sales_totals;
